package com.example.bookshop.dto;

import java.math.BigDecimal;

public record CreateBookRequestDto(String title,
                                   String author,
                                   BigDecimal price,
                                   String isbn,
                                   String description,
                                   String coverImage) {
}
