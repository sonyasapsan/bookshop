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
import com.example.bookshop.mapper.impl.BookMapperImpl;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.book.BookRepository;
import com.example.bookshop.repository.category.CategoryRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @InjectMocks
    private BookServiceImpl bookService;
    @Spy
    private BookRepository bookRepository;
    @Spy
    private BookMapper bookMapper = new BookMapperImpl();

    @Spy
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Save valid book")
    public void save_validCase_returnBookDto() {
        Book book = getBook();
        CreateBookRequestDto requestDto = getRequestDto();
        BookDto bookDto = getBookDto();
        Mockito.when(categoryRepository.findById(1L)).thenReturn(getSetCategory()
                .stream().findFirst());
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        BookDto actual = bookService.save(requestDto);
        assertEquals(actual, bookDto);
    }

    @Test
    @DisplayName("Find book by certain id")
    public void findById_validCase_returnBookToDtoWithoutCategoryIds() {
        Long bookId = 1L;
        Book book = getBook();
        BookToDtoWithoutCategoryIds bookDto = getBookToDtoWithoutCategoryIds();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
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
        CreateBookRequestDto requestDto = getRequestDto();

        Long bookId = -1L;

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
        CreateBookRequestDto requestDto = getRequestDto();
        Book updatedBook = getBook();
        Long bookId = 1L;
        Mockito.when(categoryRepository.findById(1L)).thenReturn(getSetCategory()
                .stream().findFirst());
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(updatedBook);
        BookDto expected = getBookDto();
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
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = getBookList();
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        Mockito.when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        List<BookToDtoWithoutCategoryIds> expected = getBookToDtoWithoutCategoryIdsList();
        List<BookToDtoWithoutCategoryIds> actual = bookService.getAll(pageable);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Get all books by certain author")
    public void findAllByAuthor_validCase_returnBookDtoList() {
        String author = "Billy Herrington";
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = getBookList();
        Mockito.when(bookRepository.findAllByAuthor(author, pageable)).thenReturn(books);
        List<BookDto> expected = getBookDtoList();
        List<BookDto> actual = bookService.findAllByAuthor(author, pageable);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Get all books by certain category")
    public void getAllBookByCategory_validCase_returnBookDtoList() {
        List<Book> books = getBookList();
        Pageable pageable = PageRequest.of(0, 10);
        Long categoryId = 1L;
        Mockito.when(bookRepository.findAllByCategoriesId(categoryId, pageable)).thenReturn(books);
        List<BookDto> expected = getBookDtoList();
        List<BookDto> actual = bookService.getAllBooksByCategory(categoryId, pageable);
        Assertions.assertEquals(expected, actual);
    }

    private static Book getBook() {
        Book book = new Book();
        book.setId(1L);
        book.setIsbn("9781234567890");
        book.setPrice(new BigDecimal("100"));
        book.setAuthor("An author");
        book.setTitle("A book");
        book.setCategories(getSetCategory());
        return book;
    }

    private static BookToDtoWithoutCategoryIds getBookToDtoWithoutCategoryIds() {
        return new BookToDtoWithoutCategoryIds(1L, "A book", "An author",
                "9781234567890", new BigDecimal("100"), null, null);
    }

    private static BookDto getBookDto() {
        return new BookDto(1L, "A book", "An author", "9781234567890",
                new BigDecimal("100"), null, null, Set.of(1L));
    }

    private static CreateBookRequestDto getRequestDto() {
        return new CreateBookRequestDto("A book", "An author",
                new BigDecimal("100"), "9781234567890", null,
                null, Set.of(1L));
    }

    private static List<Book> getBookList() {
        Set<Category> categorySet = getSetCategory();

        Book book = new Book();
        book.setId(1L);
        book.setTitle("A book");
        book.setAuthor("Billy Herrington");
        book.setIsbn("9781234567890");
        book.setPrice(new BigDecimal("100"));
        book.setCategories(categorySet);

        Book secondBook = new Book();
        secondBook.setId(2L);
        secondBook.setTitle("A second book");
        secondBook.setAuthor("Billy Herrington");
        secondBook.setIsbn("9781234567891");
        secondBook.setPrice(new BigDecimal("110"));
        secondBook.setCategories(categorySet);

        Book thirdBook = new Book();
        thirdBook.setId(3L);
        thirdBook.setTitle("A third book");
        thirdBook.setAuthor("Billy Herrington");
        thirdBook.setIsbn("9781234567892");
        thirdBook.setPrice(new BigDecimal("120"));
        thirdBook.setCategories(categorySet);

        return List.of(book, secondBook, thirdBook);
    }

    private static List<BookToDtoWithoutCategoryIds> getBookToDtoWithoutCategoryIdsList() {
        BookToDtoWithoutCategoryIds bookDto = new BookToDtoWithoutCategoryIds(1L,
                "A book", "Billy Herrington","9781234567890", new BigDecimal("100"),
                null, null);
        BookToDtoWithoutCategoryIds secondBookDto = new BookToDtoWithoutCategoryIds(
                2L, "A second book", "Billy Herrington",
                "9781234567891", new BigDecimal("110"), null,
                null);
        BookToDtoWithoutCategoryIds thirdBookDto = new BookToDtoWithoutCategoryIds(
                3L, "A third book", "Billy Herrington",
                "9781234567892", new BigDecimal("120"), null,
                null);
        return List.of(bookDto, secondBookDto, thirdBookDto);
    }

    private static List<BookDto> getBookDtoList() {
        BookDto bookDto = new BookDto(1L, "A book",
                "Billy Herrington", "9781234567890", new BigDecimal("100"),
                null, null, Set.of(1L));
        BookDto secondBookDto = new BookDto(2L, "A second book",
                "Billy Herrington", "9781234567891", new BigDecimal("110"),
                null, null, Set.of(1L));
        BookDto thirdBookDto = new BookDto(3L, "A third book",
                "Billy Herrington", "9781234567892", new BigDecimal("120"),
                null, null, Set.of(1L));
        return List.of(bookDto, secondBookDto, thirdBookDto);
    }

    private static Set<Category> getSetCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("horror");
        category.setDescription("something scary");
        return Set.of(category);
    }
}
