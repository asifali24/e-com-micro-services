package com.inventory_services.inventory_services.controller;


import com.inventory_services.inventory_services.dto.ProductDto;
import com.inventory_services.inventory_services.service.ProductsServices;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class Products {
    private final ProductsServices productsServices;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;


    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return ResponseEntity.ok(productsServices.getAllProducts());
    }


    @GetMapping("/test-discovery-client")
    public String test(){
        ServiceInstance instance = discoveryClient.getInstances("orders-services").getFirst();
        return restClient.get()
                .uri(instance.getUri()+"/orders/test-discovery-client")
                .retrieve()
                .body(String.class);

    }
}
