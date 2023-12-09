package com.example.bookshop.service.impl;

import com.example.bookshop.dto.order.CreateOrderDto;
import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.mapper.OrderMapper;
import com.example.bookshop.model.Order;
import com.example.bookshop.model.OrderItem;
import com.example.bookshop.model.Status;
import com.example.bookshop.repository.order.OrderRepository;
import com.example.bookshop.service.OrderItemService;
import com.example.bookshop.service.OrderService;
import com.example.bookshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Override
    public OrderDto save(CreateOrderDto requestDto) {
        return null;
    }
}
