package com.example.bookshop.dto.order;

import com.example.bookshop.dto.orderitem.OrderItemDto;
import com.example.bookshop.model.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderDto(Long id,
        Long userId,
        Set<OrderItemDto> orderItems,
        LocalDateTime orderDate,
        BigDecimal total,
        Status status){
}
