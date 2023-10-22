package com.example.bookshop.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BookDto {
    long id;
    String title;
    String author;
    String isbn;
    BigDecimal price;
    String description;
    String coverImage;
}
