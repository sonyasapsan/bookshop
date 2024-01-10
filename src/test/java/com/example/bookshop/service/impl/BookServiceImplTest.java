package com.example.bookshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookToDtoWithoutCategoryIds;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.book.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @Test
    @DisplayName("Save valid book")
    public void save_validCase_returnBookDto() {
        CreateBookRequestDto requestDto;
        requestDto = new CreateBookRequestDto("A book", "An author",
                new BigDecimal("100"), "9781234567890",
                null, null,null);
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setIsbn("9781234567890");
        book.setPrice(new BigDecimal("100"));
        book.setAuthor("An author");
        book.setTitle("A book");

        BookDto bookDto = new BookDto(book.getId(), book.getTitle(), book.getAuthor(),
                book.getIsbn(), book.getPrice(),
                book.getDescription(), book.getCoverImage(), null);

        Mockito.when(bookMapper.toBook(requestDto)).thenReturn(book);
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.save(requestDto);
        assertEquals(actual, bookDto);
    }

    @Test
    @DisplayName("Find book by certain id")
    public void findById_validCase_returnBookToDtoWithoutCategoryIds() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("A book");
        book.setAuthor("An author");
        book.setIsbn("9781234567890");
        book.setPrice(new BigDecimal("100"));

        BookToDtoWithoutCategoryIds bookDto = new BookToDtoWithoutCategoryIds(book.getId(), book.getTitle(), book.getAuthor(),
                book.getIsbn(), book.getPrice(),
                book.getDescription(), book.getCoverImage());

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toBookWithoutCategoryIds(book)).thenReturn(bookDto);
        BookToDtoWithoutCategoryIds actual = bookService.findById(bookId);
        Assertions.assertEquals(actual, bookDto);
    }

    @Test
    @DisplayName("Get exception because of invalid id while getting book")
    public void findById_invalidId_returnException() {
        Long bookId = -1L;

        Mockito.when(bookRepository.findById(bookId)).thenThrow(
                new EntityNotFoundException("Can't find book"
                        + " with this id: " + bookId)
        );

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.findById(bookId)
        );

        String excepted = "Can't find book" + " with this id: " + bookId;
        String actual = exception.getMessage();
        assertEquals(excepted, actual);
    }

    @Test
    @DisplayName("Get exception because of invalid id while updating book")
    public void update_invalidId_returnException() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto("A book", "An author",
                new BigDecimal("100"), "9781234567890",
                null, null,null);

        Long bookId = 1L;

        Mockito.lenient().when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.update(requestDto, bookId));

        String expected = "There is no book with such id: " + bookId;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update book with certain id")
    public void update_validCase_returnBookDto() {
        CreateBookRequestDto requestDto;
        requestDto = new CreateBookRequestDto("A book", "An author",
                new BigDecimal("120"), "9781234567890",
                null, null,null);
        Long bookId = 1L;
        Book updatedBook = new Book();
        updatedBook.setId(bookId);
        updatedBook.setIsbn("9781234567890");
        updatedBook.setPrice(new BigDecimal("120"));
        updatedBook.setAuthor("An author");
        updatedBook.setTitle("A book");

        BookDto expected = new BookDto(bookId, updatedBook.getTitle(),
                updatedBook.getAuthor(), updatedBook.getIsbn(), updatedBook.getPrice(),
                updatedBook.getDescription(), updatedBook.getCoverImage(), null);

        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
        Mockito.when(bookMapper.toBook(requestDto)).thenReturn(updatedBook);
        Mockito.when(bookRepository.save(updatedBook)).thenReturn(updatedBook);
        Mockito.when(bookMapper.toDto(updatedBook)).thenReturn(expected);

        BookDto actual = bookService.update(requestDto, bookId);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Delete the book by certain id")
    public void deleteById_validCase_deleted() {
        Long bookId = 1L;
        bookService.deleteById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    @DisplayName("Get all books")
    public void getAll_validCase_returnBookToDtoWithoutCategoryIdsList() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("A book");
        book.setAuthor("An author");
        book.setIsbn("9781234567890");
        book.setPrice(new BigDecimal("100"));

        Long secondBookId = 2L;
        Book secondBook = new Book();
        secondBook.setId(secondBookId);
        secondBook.setTitle("A second book");
        secondBook.setAuthor("A second author");
        secondBook.setIsbn("9781234567891");
        secondBook.setPrice(new BigDecimal("110"));

        Long thirdBookId = 1L;
        Book thirdBook = new Book();
        thirdBook.setId(thirdBookId);
        thirdBook.setTitle("A third book");
        thirdBook.setAuthor("A third author");
        thirdBook.setIsbn("9781234567892");
        thirdBook.setPrice(new BigDecimal("120"));

        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = List.of(book, secondBook, thirdBook);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        BookToDtoWithoutCategoryIds bookDto = new BookToDtoWithoutCategoryIds(book.getId(), book.getTitle(), book.getAuthor(),
                book.getIsbn(), book.getPrice(),
                book.getDescription(), book.getCoverImage());
        BookToDtoWithoutCategoryIds secondBookDto = new BookToDtoWithoutCategoryIds(secondBook.getId(), secondBook.getTitle(),
                secondBook.getAuthor(), secondBook.getIsbn(), secondBook.getPrice(),
                secondBook.getDescription(), secondBook.getCoverImage());
        BookToDtoWithoutCategoryIds thirdBookDto = new BookToDtoWithoutCategoryIds(thirdBook.getId(), thirdBook.getTitle(),
                thirdBook.getAuthor(), thirdBook.getIsbn(), thirdBook.getPrice(),
                thirdBook.getDescription(), thirdBook.getCoverImage());
        List<BookToDtoWithoutCategoryIds> expected = List.of(bookDto, secondBookDto, thirdBookDto);

        Mockito.when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        Mockito.when(bookMapper.toBookWithoutCategoryIds(book)).thenReturn(bookDto);
        Mockito.when(bookMapper.toBookWithoutCategoryIds(secondBook)).thenReturn(secondBookDto);
        Mockito.when(bookMapper.toBookWithoutCategoryIds(thirdBook)).thenReturn(thirdBookDto);

        List<BookToDtoWithoutCategoryIds> actual = bookService.getAll(pageable);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Get all books by certain author")
    public void findAllByAuthor_validCase_returnBookDtoList() {
        String author = "Billy Herrington";
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("A book");
        book.setAuthor(author);
        book.setIsbn("9781234567890");
        book.setPrice(new BigDecimal("100"));

        Long secondBookId = 2L;
        Book secondBook = new Book();
        secondBook.setId(secondBookId);
        secondBook.setTitle("A second book");
        secondBook.setAuthor(author);
        secondBook.setIsbn("9781234567891");
        secondBook.setPrice(new BigDecimal("110"));

        Long thirdBookId = 1L;
        Book thirdBook = new Book();
        thirdBook.setId(thirdBookId);
        thirdBook.setTitle("A third book");
        thirdBook.setAuthor(author);
        thirdBook.setIsbn("9781234567892");
        thirdBook.setPrice(new BigDecimal("120"));

        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = List.of(book, secondBook, thirdBook);

        BookDto bookDto = new BookDto(book.getId(), book.getTitle(), book.getAuthor(),
                book.getIsbn(), book.getPrice(),
                book.getDescription(), book.getCoverImage(), null);
        BookDto secondBookDto = new BookDto(secondBook.getId(), secondBook.getTitle(),
                secondBook.getAuthor(), secondBook.getIsbn(), secondBook.getPrice(),
                secondBook.getDescription(), secondBook.getCoverImage(), null);
        BookDto thirdBookDto = new BookDto(thirdBook.getId(), thirdBook.getTitle(),
                thirdBook.getAuthor(), thirdBook.getIsbn(), thirdBook.getPrice(),
                thirdBook.getDescription(), thirdBook.getCoverImage(), null);

        Mockito.when(bookRepository.findAllByAuthor(author, pageable)).thenReturn(books);
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);
        Mockito.when(bookMapper.toDto(secondBook)).thenReturn(secondBookDto);
        Mockito.when(bookMapper.toDto(thirdBook)).thenReturn(thirdBookDto);

        List<BookDto> expected = List.of(bookDto, secondBookDto, thirdBookDto);
        List<BookDto> actual = bookService.findAllByAuthor(author, pageable);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Get all books by certain author")
    public void getAllBookByCategory_validCase_returnBookDtoList() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        Set<Category> categories = Set.of(category);
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("A book");
        book.setAuthor("A first author");
        book.setIsbn("9781234567890");
        book.setPrice(new BigDecimal("100"));
        book.setCategories(categories);

        Long secondBookId = 2L;
        Book secondBook = new Book();
        secondBook.setId(secondBookId);
        secondBook.setTitle("A second book");
        secondBook.setAuthor("A second author");
        secondBook.setIsbn("9781234567891");
        secondBook.setPrice(new BigDecimal("110"));
        secondBook.setCategories(categories);
        List<Book> books = List.of(book, secondBook);

        BookDto bookDto = new BookDto(book.getId(), book.getTitle(), book.getAuthor(),
                book.getIsbn(), book.getPrice(),
                book.getDescription(), book.getCoverImage(), Set.of(1L));
        BookDto secondBookDto = new BookDto(secondBook.getId(), secondBook.getTitle(),
                secondBook.getAuthor(), secondBook.getIsbn(), secondBook.getPrice(),
                secondBook.getDescription(), secondBook.getCoverImage(), Set.of(1L));

        Mockito.when(bookRepository.findAllByCategoriesId(categoryId)).thenReturn(books);
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);
        Mockito.when(bookMapper.toDto(secondBook)).thenReturn(secondBookDto);

        List<BookDto> expected = List.of(bookDto, secondBookDto);
        List<BookDto> actual = bookService.getAllBooksByCategory(categoryId);
        Assertions.assertEquals(expected, actual);
    }
}