package com.inventory_services.inventory_services.service;

import com.inventory_services.inventory_services.dto.ProductDto;
import com.inventory_services.inventory_services.entity.Products;
import com.inventory_services.inventory_services.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsServices {
    private final ProductsRepository productsRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllProducts() {
        List<Products> products = productsRepository.findAll();

        return products
                .stream()
                .map(e -> modelMapper.map(e,ProductDto.class))
                .toList();

    }
}
