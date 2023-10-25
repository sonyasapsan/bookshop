package com.example.bookshop.dto;

import com.example.bookshop.validation.Isbn;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateBookRequestDto(@NotNull
                                   String title,
                                   @NotNull
                                   String author,
                                   @NotNull
                                   @Min(0)
                                   BigDecimal price,
                                   @Isbn
                                   @NotNull
                                   String isbn,
                                   String description,
                                   String coverImage) {
}
