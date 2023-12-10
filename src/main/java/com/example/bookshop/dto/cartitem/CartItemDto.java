package com.example.bookshop.dto.cartitem;

public record CartItemDto(Long id,
                          Long bookId,
                          String bookTitle,
                          int quantity) {
}
