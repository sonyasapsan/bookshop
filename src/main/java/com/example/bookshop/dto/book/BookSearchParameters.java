package com.example.bookshop.dto.book;

public record BookSearchParameters(String[] titles,
                                   String[] authors) {
}
