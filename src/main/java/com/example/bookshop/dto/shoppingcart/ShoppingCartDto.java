package com.example.bookshop.dto.shoppingcart;

import com.example.bookshop.dto.cartitem.CartItemDto;
import java.util.Set;

public record ShoppingCartDto(Long id,
                              Long userId,
                              Set<CartItemDto> cartItems) {
}
