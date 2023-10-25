package com.example.bookshop.service.impl;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.BookSearchParameters;
import com.example.bookshop.dto.CreateBookRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.SpecificationBuilder;
import com.example.bookshop.repository.specification.impl.BookSpecificationBuilderImpl;
import com.example.bookshop.service.BookService;
import java.util.List;
import java.util.Optional;
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
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream()
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
    public BookDto update(BookDto bookDto, Long id) {
        Optional<Book> optionalBookFromDB = bookRepository.findById(id);
        Book bookFromDB = optionalBookFromDB
                .orElseThrow(() -> new EntityNotFoundException("Can't find book"
                + " with this id: " + id + " to change it"));
        bookFromDB.setTitle(bookDto.title());
        bookFromDB.setAuthor(bookDto.author());
        bookFromDB.setPrice(bookDto.price());
        bookFromDB.setDescription(bookDto.description());
        bookFromDB.setCoverImage(bookDto.coverImage());
        bookFromDB.setId(id);
        bookFromDB.setIsbn(bookDto.isbn());
        return bookMapper.toDto(bookRepository.save(bookFromDB));
    }

    @Override
    public List<BookDto> search(BookSearchParameters bookSearchParameters) {
        Specification<Book> bookSpecification = bookSpecificationBuilder
                .build(bookSearchParameters);
        return bookRepository.findAll(bookSpecification).stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
