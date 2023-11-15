package com.example.bookshop.dto.book;

import com.example.bookshop.validation.Isbn;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.Length;

public record CreateBookRequestDto(@NotNull
                                   @Length(max = 250)
                                   @NotEmpty
                                   String title,
                                   @NotNull
                                   @NotEmpty
                                   @Length(max = 100)
                                   String author,
                                   @NotNull
                                   @PositiveOrZero
                                   BigDecimal price,
                                   @Isbn
                                   @NotEmpty
                                   @Length(max = 15)
                                   String isbn,
                                   String description,
                                   String coverImage) {
}
