package com.example.bookshop.repository.impl;

import com.example.bookshop.exception.DataProcessingException;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Book save(Book book) {
        EntityTransaction entityTransaction = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(book);
            entityTransaction.commit();
            return book;
        } catch (RuntimeException e) {
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw new DataProcessingException("Can't save book to DB, cause: " + e);
        }
    }

    @Override
    public List<Book> getAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery("FROM Book", Book.class).getResultList();
        } catch (RuntimeException e) {
            throw new DataProcessingException("Can't get all books, cause: " + e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Book book = entityManager.find(Book.class, id);
            return Optional.ofNullable(book);
        } catch (RuntimeException e) {
            throw new DataProcessingException("Can't get book by id " + id + ", cause: " + e);
        }
    }

    @Override
    public List<Book> findAllByAuthor(String author) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager
                    .createQuery("SELECT b FROM Book b"
                            + " WHERE lower(b.author) LIKE :author", Book.class)
                    .setParameter("author", "%" + author.toLowerCase() + "%")
                    .getResultList();
        } catch (RuntimeException e) {
            throw new DataProcessingException("Can't get all books where author is "
                    + author + ", cause: " + e);
        }
    }
}
