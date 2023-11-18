package com.example.bookshop.service.impl;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookSearchParameters;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.book.BookRepository;
import com.example.bookshop.repository.book.SpecificationBuilder;
import com.example.bookshop.repository.book.specification.impl.BookSpecificationBuilderImpl;
import com.example.bookshop.service.BookService;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final SpecificationBuilder<Book> bookSpecificationBuilder;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper,
                           BookSpecificationBuilderImpl bookSpecificationBuilder1) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.bookSpecificationBuilder = bookSpecificationBuilder1;
    }

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toBook(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> findAllByAuthor(String author, Pageable pageable) {
        return bookRepository.findAllByAuthor(author, pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book"
                        + " with this id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(CreateBookRequestDto createBookRequestDto, Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("There is no book with such id: " + id);
        }
        Book book = bookMapper.toBook(createBookRequestDto);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> search(BookSearchParameters bookSearchParameters, Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder
                .build(bookSearchParameters);
        return bookRepository.findAll(bookSpecification, pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
