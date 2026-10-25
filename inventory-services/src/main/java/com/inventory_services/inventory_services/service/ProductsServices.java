package com.inventory_services.inventory_services.service;

import com.inventory_services.inventory_services.dto.OrderRequestDto;
import com.inventory_services.inventory_services.dto.OrderRequestItemDto;
import com.inventory_services.inventory_services.dto.ProductDto;
import com.inventory_services.inventory_services.entity.Products;
import com.inventory_services.inventory_services.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
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
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        if (orderRequestDto == null || orderRequestDto.getItems() == null || orderRequestDto.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        double totalPrice = 0;
        for (OrderRequestItemDto item : orderRequestDto.getItems()) {
            if (item.getProductId() == null || item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Each order item requires a productId and a positive quantity");
            }

            Products product = productsRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + item.getProductId()));
            if (product.getStocks() < item.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + item.getProductId());
            }

            product.setStocks(product.getStocks() - item.getQuantity());
            totalPrice += product.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }
}
