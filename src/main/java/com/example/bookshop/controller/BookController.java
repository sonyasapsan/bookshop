package com.example.bookshop.controller;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.BookSearchParameters;
import com.example.bookshop.dto.CreateBookRequestDto;
import com.example.bookshop.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/author")
    public List<BookDto> findAllByAuthor(@RequestParam String author) {
        return bookService.findAllByAuthor(author);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookDto create(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.getAll();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/{id}")
    public BookDto update(@RequestBody @Valid CreateBookRequestDto requestDto,
                          @PathVariable @Positive Long id) {
        return bookService.update(requestDto, id);
    }

    @GetMapping("/search")
    public List<BookDto> search(BookSearchParameters bookSearchParameters) {
        return bookService.search(bookSearchParameters);
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }
}
