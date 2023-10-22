package com.example.bookshop.controller;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.CreateBookRequestDto;
import com.example.bookshop.model.Book;
import com.example.bookshop.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController// Controller + ResponseBody
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/by-author")
    public List<BookDto> findAllByAuthor(@RequestParam String author) {
        return bookService.findAllByAuthor(author);
    }

    @PostMapping
    public BookDto save(@RequestBody CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }
}
