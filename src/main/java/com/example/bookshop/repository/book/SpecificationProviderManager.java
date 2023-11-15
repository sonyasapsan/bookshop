package com.example.bookshop.repository.book;

import com.example.bookshop.repository.book.SpecificationProvider;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getSpecificationProvider(String key);
}
