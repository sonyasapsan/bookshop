package com.example.bookshop.repository.book.specification.impl;

import com.example.bookshop.dto.book.BookSearchParameters;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.book.SpecificationBuilder;
import com.example.bookshop.repository.book.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BookSpecificationBuilderImpl implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    public BookSpecificationBuilderImpl(SpecificationProviderManager<Book>
                                                bookSpecificationProviderManager) {
        this.bookSpecificationProviderManager = bookSpecificationProviderManager;
    }

    @Override
    public Specification<Book> build(BookSearchParameters bookSearchParameters) {
        Specification<Book> specification = Specification.where(null);
        if (bookSearchParameters.titles() != null && bookSearchParameters.titles().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(bookSearchParameters.titles()));
        }
        if (bookSearchParameters.authors() != null && bookSearchParameters.authors().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(bookSearchParameters.authors()));
        }
        return specification;
    }
}
