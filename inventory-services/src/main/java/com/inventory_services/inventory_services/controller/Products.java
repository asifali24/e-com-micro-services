package com.inventory_services.inventory_services.controller;


import com.inventory_services.inventory_services.dto.ProductDto;
import com.inventory_services.inventory_services.service.ProductsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class Products {
    private final ProductsServices productsServices;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return ResponseEntity.ok(productsServices.getAllProducts());
    }
}
