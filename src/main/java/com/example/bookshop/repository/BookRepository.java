package com.example.bookshop.repository;

import com.example.bookshop.model.Book;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAuthor(String author);

}
