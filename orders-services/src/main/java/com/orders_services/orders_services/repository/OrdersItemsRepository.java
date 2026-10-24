package com.orders_services.orders_services.repository;

import com.orders_services.orders_services.entity.OrdersItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface OrdersItemsRepository extends JpaRepository<OrdersItems, UUID> {
}