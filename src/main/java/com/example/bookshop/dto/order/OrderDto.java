package com.example.bookshop.dto.order;

import com.example.bookshop.model.OrderItem;
import com.example.bookshop.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class OrderDto{
    private Long id;
    private Long userId;
    private Set<OrderItem> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Status status;
}
