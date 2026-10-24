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
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    private UUID id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Integer totalPrice;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<OrdersItems> ordersItems;

}
