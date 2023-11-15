package com.example.bookshop.service;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookSearchParameters;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> getAll(Pageable pageable);

    List<BookDto> findAllByAuthor(String author);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(CreateBookRequestDto createBookRequestDto, Long id);

    List<BookDto> search(BookSearchParameters bookSearchParameters);
}
