package com.orders_services.orders_services.dto;

import lombok.Data;

import java.util.UUID;


@Data
public class OrderRequestItemsDto {
    private UUID id;
    private UUID productId;
    private Integer quantity;
}
