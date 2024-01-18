package com.example.bookshop.repository;

import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.book.BookRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Sql(scripts = "classpath:database/category/book/insert-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/category/book/delete-from-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Find all by the certain author")
    public void findAllByAuthor_validCade_returnBookList() {
        String author = "An author";
        Book book = new Book();
        book.setId(1);
        book.setAuthor("An author");
        book.setCoverImage("image.url");
        book.setIsbn("97834234325");
        book.setDescription("no description");
        book.setPrice(new BigDecimal(100));
        List<Book> expected = List.of(book);

        Pageable pageable = PageRequest.of(0, 10);

        List<Book> actual = bookRepository.findAllByAuthor(author, pageable);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Sql(scripts = {"classpath:database/category/book/insert-books.sql",
            "classpath:database/category/category/insert-category.sql",
            "classpath:database/category/book/connect-category-to-book.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/category/book/delete-connection.sql",
            "classpath:database/category/book/delete-from-books.sql",
            "classpath:database/category/category/delete-from-categories.sql",
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Find all by the certain category")
    public void findAllByCategoriesId_validCade_returnBookList() {
        Category category = new Category();
        category.setName("Comedy");
        category.setDescription("something funny");
        category.setId(1L);

        Book book = new Book();
        book.setId(1);
        book.setAuthor("An author");
        book.setCoverImage("image.url");
        book.setIsbn("97834234325");
        book.setDescription("no description");
        book.setPrice(new BigDecimal(100));
        book.setCategories(Set.of(category));
        List<Book> expected = List.of(book);
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> actual = bookRepository.findAllByCategoriesId(1L, pageable);
        EqualsBuilder.reflectionEquals(expected, actual);
    }
}
