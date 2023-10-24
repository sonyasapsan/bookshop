package com.example.bookshop.service.impl;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.CreateBookRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.service.BookService;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private static final String ISBN_START = "978";
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private StringBuilder stringBuilder;
    private Random random;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        random = new Random();
    }

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toBook(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepository.getAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> findAllByAuthor(String author) {
        return bookRepository.findAllByAuthor(author).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book"
                        + " with this id: " + id)));
    }
}
