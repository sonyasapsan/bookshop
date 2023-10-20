package com.example.bookshop;

import com.example.bookshop.model.Book;
import com.example.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class BookShopApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookShopApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("Book 1");
            book.setAuthor("Sonia");
            book.setIsbn("isbn 1");
            book.setPrice(new BigDecimal(100));
            Book book2 = new Book();
            book2.setTitle("Book 2");
            book2.setAuthor("Diana");
            book2.setIsbn("isbn 2");
            book2.setPrice(new BigDecimal(80));
            bookService.save(book);
            bookService.save(book2);
            System.out.println(bookService.findAll());
        };
    }
}
