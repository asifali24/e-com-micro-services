package com.orders_services.orders_services.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Data
public class OrderRequestDto {

    private UUID id;
    private List<OrderRequestItemsDto> items;
    private BigDecimal totalPrice;
}
