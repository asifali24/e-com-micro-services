package com.orders_services.orders_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrdersServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersServicesApplication.class, args);
	}

}
