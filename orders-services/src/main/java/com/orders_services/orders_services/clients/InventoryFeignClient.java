package com.orders_services.orders_services.clients;

import com.orders_services.orders_services.dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-services", path = "/products")
public interface InventoryFeignClient {

    @PutMapping("/reduce-stocks")
    Double reduceStocks(@RequestBody OrderRequestDto orderRequestDto);
}
