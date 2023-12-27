package com.example.bookshop.repository.book;

import com.example.bookshop.model.Book;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> findAllByAuthor(String author, Pageable pageable);

    List<Book> findAllByCategoriesId(Long categoryId);
}