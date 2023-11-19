package com.example.bookshop.dto.book;

import com.example.bookshop.validation.isbn.validator.Isbn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Set;
import org.hibernate.validator.constraints.Length;

public record CreateBookRequestDto(@NotBlank
                                   @Length(max = 250)
                                   String title,
                                   @NotBlank
                                   @Length(max = 100)
                                   String author,
                                   @NotNull
                                   @PositiveOrZero
                                   BigDecimal price,
                                   @Isbn
                                   @NotBlank
                                   @Length(max = 15)
                                   String isbn,
                                   String description,
                                   String coverImage,
                                   Set<Long> categoriesIds) {
}
