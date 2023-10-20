package com.example.bookshop.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable = false)
    String title;
    @Column(nullable = false)
    String author;
    @Column(unique = true, nullable = false)
    String isbn;
    @Column(nullable = false)
    BigDecimal price;
    String description;
    String coverImage;
}
