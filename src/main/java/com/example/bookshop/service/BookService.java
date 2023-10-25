package com.example.bookshop.service;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.BookSearchParameters;
import com.example.bookshop.dto.CreateBookRequestDto;
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
