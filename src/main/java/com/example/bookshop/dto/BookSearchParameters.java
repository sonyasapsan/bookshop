package com.example.bookshop.dto;

public record BookSearchParameters(String[] titles,
                                   String[] authors) {
}
