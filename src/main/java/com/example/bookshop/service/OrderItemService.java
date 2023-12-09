package com.example.bookshop.service;

import com.example.bookshop.model.OrderItem;
import com.example.bookshop.model.ShoppingCart;

import java.util.Set;

public interface OrderItemService {
    Set<OrderItem> getOrderItems();
}
