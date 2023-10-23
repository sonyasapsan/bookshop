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
        Book book = bookMapper.toModel(requestDto);
        book.setIsbn(generateIsbn());
        return bookMapper.toBook(bookRepository.save(book));
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toBook)
                .toList();
    }

    @Override
    public List<BookDto> findAllByAuthor(String author) {
        return bookRepository.findAllByAuthor(author).stream()
                .map(bookMapper::toBook)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toBook(bookRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book"
                        + " with this id: " + id)));
    }

    private String generateIsbn() {
        stringBuilder = new StringBuilder(ISBN_START);
        IntStream.range(0, 10)
                .forEach(i -> stringBuilder.append(random.nextInt(10)));
        return stringBuilder.toString();
    }
}
