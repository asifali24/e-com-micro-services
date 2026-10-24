package com.inventory_services.inventory_services.repository;

import com.inventory_services.inventory_services.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ProductsRepository extends JpaRepository<Products, UUID> {
}