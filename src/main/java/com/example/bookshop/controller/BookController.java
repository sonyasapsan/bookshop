package com.example.bookshop.controller;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.BookSearchParameters;
import com.example.bookshop.dto.CreateBookRequestDto;
import com.example.bookshop.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "find all books where authors with certain name",
                description = "find all books by authors")
    @GetMapping("/{author}")
    public List<BookDto> findAllByAuthor(@PathVariable String author,
                                         @PageableDefault Pageable pageable) {
        return bookService.findAllByAuthor(author, pageable);
    }

    @Operation(summary = "Create a new book", description = "add information to db")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookDto create(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @Operation(summary = "Get all books", description = "amount can be limited by parameter")
    @GetMapping
    public List<BookDto> getAll(@PageableDefault Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @Operation(summary = "Delete the certain book", description = "Delete the book from DB by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive Long id) {
        bookService.deleteById(id);
    }

    @Operation(summary = "Update the certain book", description = "update the certain book by id")
    @PutMapping("/{id}")
    public BookDto update(@RequestBody @Valid CreateBookRequestDto requestDto,
                          @PathVariable @Positive Long id) {
        return bookService.update(requestDto, id);
    }

    @Operation(summary = "Get all books which meet some requirements", description = "filtering")
    @GetMapping("/search")
    public List<BookDto> search(BookSearchParameters bookSearchParameters, @PageableDefault Pageable pageable) {
        return bookService.search(bookSearchParameters, pageable);
    }

    @Operation(summary = "Find the certain book by id", description = "searching the certain book")
    @GetMapping("/{id}")
    public BookDto findById(@PathVariable @Positive Long id) {
        return bookService.findById(id);
    }
}
