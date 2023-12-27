package com.example.bookshop.service;

import com.example.bookshop.dto.order.CreateOrderDto;
import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.dto.order.UpdateOrderStatusDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

public interface OrderService {
    OrderDto save(CreateOrderDto requestDto);

    List<OrderDto> getAllOrders(@PageableDefault Pageable pageable);

    OrderDto updateOrderStatus(UpdateOrderStatusDto updateOrderStatusDto, Long id);
}