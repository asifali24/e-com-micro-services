package com.orders_services.orders_services.service;

import com.orders_services.orders_services.clients.InventoryFeignClient;
import com.orders_services.orders_services.dto.OrderRequestDto;
import com.orders_services.orders_services.dto.OrderRequestItemsDto;
import com.orders_services.orders_services.entity.Orders;
import com.orders_services.orders_services.entity.OrdersItems;
import com.orders_services.orders_services.enums.OrderStatus;
import com.orders_services.orders_services.repository.OrdersRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersServices {

    private final OrdersRepository ordersRepository;
    private final InventoryFeignClient inventoryFeignClient;

    @RateLimiter(name = "orderCreation", fallbackMethod = "createOrderFallback")
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "createOrderFallback")
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        long startedAt = System.nanoTime();
        validateOrder(orderRequestDto);
        log.info("Creating order with {} item(s)", orderRequestDto.getItems().size());

        log.info("Requesting stock reduction from inventory service");
        Double totalPrice = inventoryFeignClient.reduceStocks(orderRequestDto);
        log.info("Inventory stock reduction completed; calculated total price: {}", totalPrice);
        Orders order = new Orders();
        order.setOrderStatus(OrderStatus.APPROVED);
        order.setTotalPrice(BigDecimal.valueOf(totalPrice));

        List<OrdersItems> items = new ArrayList<>();
        for (OrderRequestItemsDto itemRequest : orderRequestDto.getItems()) {
            OrdersItems item = new OrdersItems();
            item.setProductId(itemRequest.getProductId());
            item.setQuantity(itemRequest.getQuantity());
            item.setOrders(order);
            items.add(item);
        }
        order.setOrdersItems(items);

        OrderRequestDto savedOrder = toDto(ordersRepository.save(order));
        long durationMs = (System.nanoTime() - startedAt) / 1_000_000;
        log.info("Order created successfully with id: {} in {} ms", savedOrder.getId(), durationMs);
        return savedOrder;
    }

    public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto, Throwable throwable) {
        log.error("Inventory service unavailable while creating order", throwable);
        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Inventory service is temporarily unavailable"
        );
    }

    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all orders");
        return ordersRepository.findAll().stream().map(this::toDto).toList();
    }

    public OrderRequestDto getOrdersById(UUID id) {
        log.info("Fetching order by id: {}", id);
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return toDto(order);
    }

    private void validateOrder(OrderRequestDto orderRequestDto) {
        if (orderRequestDto == null || orderRequestDto.getItems() == null || orderRequestDto.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        for (OrderRequestItemsDto item : orderRequestDto.getItems()) {
            if (item.getProductId() == null || item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Each order item requires a productId and a positive quantity");
            }
        }
    }

    private OrderRequestDto toDto(Orders order) {
        OrderRequestDto dto = new OrderRequestDto();
        dto.setId(order.getId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setItems(order.getOrdersItems() == null ? List.of() : order.getOrdersItems().stream().map(item -> {
            OrderRequestItemsDto itemDto = new OrderRequestItemsDto();
            itemDto.setId(item.getId());
            itemDto.setProductId(item.getProductId());
            itemDto.setQuantity(item.getQuantity());
            return itemDto;
        }).toList());
        return dto;
    }
}
