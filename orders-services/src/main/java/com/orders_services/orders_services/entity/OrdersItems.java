package com.orders_services.orders_services.entity;


import com.orders_services.orders_services.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
public class OrdersItems {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    private UUID id;

    private UUID productId;

    private Integer quantity;


    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Orders orders;

}
