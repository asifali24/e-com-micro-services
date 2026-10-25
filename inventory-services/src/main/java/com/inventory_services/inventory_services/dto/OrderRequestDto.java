package com.inventory_services.inventory_services.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderRequestDto {
    private UUID id;
    private List<OrderRequestItemDto> items;
}
