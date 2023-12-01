package com.example.bookshop.service;

import com.example.bookshop.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookshop.dto.shoppingcart.ShoppingCartDto;

public interface ShoppingCartService {
    void addItem(CreateCartItemRequestDto createCartItemRequestDto);

    ShoppingCartDto getShoppingCart();
}
