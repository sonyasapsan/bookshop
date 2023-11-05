package com.example.bookshop.dto;

import com.example.bookshop.validation.Isbn;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record CreateBookRequestDto(@NotNull
                                   @Length(max = 250)
                                   String title,
                                   @NotNull
                                   @Length(max = 100)
                                   String author,
                                   @NotNull
                                   @Min(0)
                                   BigDecimal price,
                                   @Isbn
                                   @NotNull
                                   @Length(max = 15)
                                   String isbn,
                                   String description,
                                   String coverImage) {
}
