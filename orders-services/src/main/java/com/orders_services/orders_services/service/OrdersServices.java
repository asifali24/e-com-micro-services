package com.orders_services.orders_services.service;


import com.orders_services.orders_services.dto.OrderRequestDto;
import com.orders_services.orders_services.dto.OrderRequestItemsDto;
import com.orders_services.orders_services.entity.Orders;
import com.orders_services.orders_services.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersServices {

    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;

    public List<OrderRequestDto> getAllOrders(){
        log.info("Fetching all orders......");
      List<Orders> orders = ordersRepository.findAll();
      return orders.stream().map(e -> modelMapper.map(e, OrderRequestDto.class)).toList();
    }

    public OrderRequestDto getOrdersById(UUID id){

        log.info("fetching order request by id : {}",id);

        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resources not found with id: "+id ));

        return modelMapper.map(orders,OrderRequestDto.class);

    }


}
