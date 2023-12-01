package com.example.bookshop.dto.cartitem;

import jakarta.validation.constraints.Positive;

public record CreateCartItemRequestDto(Long bookId,
                                       @Positive
                                       int quantity) {
}
