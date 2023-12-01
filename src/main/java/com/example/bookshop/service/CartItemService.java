package com.example.bookshop.service;

import com.example.bookshop.dto.cartitem.UpdateCartItemRequestDto;

public interface CartItemService {
    void update(UpdateCartItemRequestDto updateCartItemRequestDto, Long id);

    void delete(Long id);
}
