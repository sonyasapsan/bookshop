package com.example.bookshop.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateBookRequestDto {
    private String title;
    private String author;
    private BigDecimal price;
    private String description;
    private String coverImage;
}
