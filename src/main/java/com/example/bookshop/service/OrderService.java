package com.example.bookshop.service;

import com.example.bookshop.dto.order.CreateOrderDto;
import com.example.bookshop.dto.order.OrderDto;

public interface OrderService {
    OrderDto save(CreateOrderDto requestDto);
}
