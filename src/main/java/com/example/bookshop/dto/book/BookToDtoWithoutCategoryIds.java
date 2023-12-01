package com.example.bookshop.dto.book;

import java.math.BigDecimal;

public record BookToDtoWithoutCategoryIds(Long id,
                                          String title,
                                          String author,
                                          String isbn,
                                          BigDecimal price,
                                          String description,
                                          String coverImage) {
}
