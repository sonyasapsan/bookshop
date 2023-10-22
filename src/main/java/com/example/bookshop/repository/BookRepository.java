package com.example.bookshop.repository;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    List<Book> getAll();

    Optional<Book> findById(Long id);

    List<Book> findAllByAuthor(String author);
}
