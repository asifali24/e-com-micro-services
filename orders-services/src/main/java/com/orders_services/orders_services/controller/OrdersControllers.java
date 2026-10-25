package com.orders_services.orders_services.controller;


import com.orders_services.orders_services.dto.OrderRequestDto;
import com.orders_services.orders_services.service.OrdersServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrdersControllers {

    private final OrdersServices ordersServices;

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders(){
        log.info("Fetching all the orders via controllers");
        return ResponseEntity.ok(ordersServices.getAllOrders());
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable UUID id){
        log.info("Fetching orders by id: {} via controllers",id);
        return ResponseEntity.ok(ordersServices.getOrdersById(id));
    }

    @GetMapping("/test-discovery-client")
    public String test(){
        return "Testing Discovery & rest client";

    }


}
