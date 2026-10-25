package com.inventory_services.inventory_services.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderRequestItemDto {
    private UUID productId;
    private Integer quantity;
}
