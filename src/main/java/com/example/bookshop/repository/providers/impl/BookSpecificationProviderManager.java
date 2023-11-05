package com.example.bookshop.repository.providers.impl;

import com.example.bookshop.model.Book;
import com.example.bookshop.repository.SpecificationProvider;
import com.example.bookshop.repository.SpecificationProviderManager;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    public BookSpecificationProviderManager(List<SpecificationProvider<Book>>
                                                    bookSpecificationProviders) {
        this.bookSpecificationProviders = bookSpecificationProviders;
    }

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find specification"
                        + " provider for key" + key));
    }
}
