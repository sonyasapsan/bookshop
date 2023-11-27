package com.example.bookshop.service.impl;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookSearchParameters;
import com.example.bookshop.dto.book.BookToDtoWithoutCategoryIds;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.book.BookRepository;
import com.example.bookshop.repository.book.SpecificationBuilder;
import com.example.bookshop.repository.category.CategoryRepository;
import com.example.bookshop.service.BookService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final SpecificationBuilder<Book> bookSpecificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toBook(requestDto);
        book = saveOrUpdateCategories(book, requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookToDtoWithoutCategoryIds> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toBookWithoutCategoryIds)
                .toList();
    }

    @Override
    public List<BookDto> findAllByAuthor(String author, Pageable pageable) {
        return bookRepository.findAllByAuthor(author, pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookToDtoWithoutCategoryIds findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toBookWithoutCategoryIds)
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
        book = saveOrUpdateCategories(book, createBookRequestDto);
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

    private Book saveOrUpdateCategories(Book book, CreateBookRequestDto requestDto) {
        Set<Category> categories = requestDto.categoriesIds().stream()
                .map(categoryRepository::findById)
                .map(o -> o.orElseThrow(() -> new EntityNotFoundException("Can't"
                        + " find category.")))
                .collect(Collectors.toSet());
        book.setCategories(categories);
        return book;
    }

    @Override
    public List<BookDto> getAllBooksByCategory(Long id) {
        return bookRepository.findAllByCategoriesId(id)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
