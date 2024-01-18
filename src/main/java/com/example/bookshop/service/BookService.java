package com.example.bookshop.service;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookSearchParameters;
import com.example.bookshop.dto.book.BookToDtoWithoutCategoryIds;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookToDtoWithoutCategoryIds> getAll(Pageable pageable);

    List<BookDto> findAllByAuthor(String author, Pageable pageable);

    BookToDtoWithoutCategoryIds findById(Long id);

    void deleteById(Long id);

    BookDto update(CreateBookRequestDto createBookRequestDto, Long id);

    List<BookDto> search(BookSearchParameters bookSearchParameters, Pageable pageable);

    List<BookDto> getAllBooksByCategory(Long id, Pageable pageable);
}
