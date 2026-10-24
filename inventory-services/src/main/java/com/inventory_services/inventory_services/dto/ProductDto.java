package com.inventory_services.inventory_services.dto;

import lombok.Data;

import java.util.UUID;


@Data
public class ProductDto {
    private UUID id;

    private String title;

    private Double price;

    private Integer stocks;
}
