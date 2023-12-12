package com.example.bookshop.service;

import com.example.bookshop.dto.orderitem.OrderItemDto;
import com.example.bookshop.model.Order;
import com.example.bookshop.model.OrderItem;
import com.example.bookshop.model.ShoppingCart;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

public interface OrderItemService {
    Set<OrderItem> getOrderItems();

    void saveOrderItems(Order order);

    List<OrderItemDto> getItemsFromOrder(Long id);

    OrderItemDto getItemFromOrderById(Long id);
}
