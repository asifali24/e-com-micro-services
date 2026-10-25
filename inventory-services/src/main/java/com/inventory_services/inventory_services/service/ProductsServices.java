package com.inventory_services.inventory_services.service;

import com.inventory_services.inventory_services.dto.OrderRequestDto;
import com.inventory_services.inventory_services.dto.OrderRequestItemDto;
import com.inventory_services.inventory_services.dto.ProductDto;
import com.inventory_services.inventory_services.entity.Products;
import com.inventory_services.inventory_services.repository.ProductsRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsServices {
    private final ProductsRepository productsRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllProducts() {
        return productsRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    @Transactional
    @CircuitBreaker(name = "inventoryDatabase", fallbackMethod = "reduceStocksFallback")
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        long startedAt = System.nanoTime();
        if (orderRequestDto == null || orderRequestDto.getItems() == null || orderRequestDto.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        log.info("Reducing inventory for {} item(s)", orderRequestDto.getItems().size());

        double totalPrice = 0;
        for (OrderRequestItemDto item : orderRequestDto.getItems()) {
            if (item.getProductId() == null || item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Each order item requires a productId and a positive quantity");
            }

            Products product = productsRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + item.getProductId()));
            if (product.getStocks() < item.getQuantity()) {
                log.warn("Insufficient stock for product: {}", item.getProductId());
                throw new IllegalArgumentException("Insufficient stock for product: " + item.getProductId());
            }

            product.setStocks(product.getStocks() - item.getQuantity());
            totalPrice += product.getPrice() * item.getQuantity();
        }
        long durationMs = (System.nanoTime() - startedAt) / 1_000_000;
        log.info("Inventory reduced successfully; total price: {} in {} ms", totalPrice, durationMs);
        return totalPrice;
    }

    public Double reduceStocksFallback(OrderRequestDto orderRequestDto, Throwable throwable) {
        log.error("Inventory database unavailable while reducing stock", throwable);
        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Inventory service is temporarily unavailable"
        );
    }
}
