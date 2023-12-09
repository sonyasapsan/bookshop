package com.example.bookshop.service.impl;

import com.example.bookshop.dto.order.CreateOrderDto;
import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.mapper.OrderItemMapper;
import com.example.bookshop.model.OrderItem;
import com.example.bookshop.model.ShoppingCart;
import com.example.bookshop.service.OrderItemService;
import com.example.bookshop.service.OrderService;
import com.example.bookshop.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    @Override
    public Set<OrderItem> getOrderItems() {
        return null;
    }
}
