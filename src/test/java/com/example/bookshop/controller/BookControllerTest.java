package com.example.bookshop.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookToDtoWithoutCategoryIds;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Sql(scripts = "classpath:database/category/book/insert-books.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/category/book/delete-from-books.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Save new book")
    public void create_validCase_returnBookDto() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto("Java", "An author",
                new BigDecimal("100"), "9781234567890",
                null, null, null);
        BookDto expected = new BookDto(3L, "A book", "An author", "9781234567890",
                new BigDecimal("100"), null, null, null);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult result = mockMvc.perform(post("/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse()
                        .getContentAsString(), BookDto.class);
        EqualsBuilder.reflectionEquals(actual, expected);
    }

    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    @Test
    @DisplayName("Get the certain book by id")
    public void findById_validCase_returnBookToDtoWithoutCategoryIds() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto("A book", "An author",
                new BigDecimal("100"), "97834234325",
                 "no description", "image.url", null);
        Long bookId = 1L;
        BookToDtoWithoutCategoryIds expected = new BookToDtoWithoutCategoryIds(bookId, "A book",
                "An author", "97834234325", new BigDecimal(100),
                "no description", "image.url");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult result = mockMvc.perform(get("/books/" + bookId)
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookToDtoWithoutCategoryIds actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), BookToDtoWithoutCategoryIds.class);
        EqualsBuilder.reflectionEquals(actual, expected);
    }

    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    @Test
    @DisplayName("Get all books")
    public void getAll_validCase_returnListBookToDtoWithoutCategoryIds() throws Exception {
        CreateBookRequestDto firstRequestDto = new CreateBookRequestDto("A book",
                "An author", new BigDecimal("100"), "97834234325",
                "no description", "image.url", null);
        CreateBookRequestDto secondRequestDto = new CreateBookRequestDto("A book 2.0",
                "An author 2.0", new BigDecimal(120), "97834234326",
                "no description", "image2.url", null);
        BookToDtoWithoutCategoryIds firstBook = new BookToDtoWithoutCategoryIds(1L,
                "A book", "An author", "97834234325", new BigDecimal(100),
                "no description", "image.url");
        BookToDtoWithoutCategoryIds secondBook = new BookToDtoWithoutCategoryIds(2L,
                "A book 2.0", "An author 2.0", "97834234326", new BigDecimal(120),
                "no description", "image2.url");
        List<BookToDtoWithoutCategoryIds> expected = List.of(firstBook, secondBook);
        String jsonRequest = objectMapper.writeValueAsString(List.of(
                            firstRequestDto, secondRequestDto
        ));

        MvcResult result = mockMvc.perform(get("/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<BookToDtoWithoutCategoryIds> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class,
                        BookToDtoWithoutCategoryIds.class)
        );

        EqualsBuilder.reflectionEquals(actual, expected);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update a book by id")
    public void update_validCase_returnBookDto() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto("Java",
                "An author", new BigDecimal("100"), "9781234567890",
                null, null, null);
        Long bookId = 1L;
        BookToDtoWithoutCategoryIds expected = new BookToDtoWithoutCategoryIds(bookId,
                "A book", "An author", "97834234325", new BigDecimal(150),
                "no description", "image.url");
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/books/" + bookId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        EqualsBuilder.reflectionEquals(actual, expected);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete the certain book by id")
    public void delete_validCase_returnStatus204() throws Exception {
        Long bookId = 1L;
        MvcResult result = mockMvc.perform(delete("/books/" + bookId))
                .andExpect(status().isNoContent())
                .andReturn();
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    @Test
    @DisplayName("Find books by author")
    public void findAllByAuthor_validCase_returnListBookDto() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto("A book",
                "An author", new BigDecimal("100"), "97834234325",
                "no description", "image.url", null);
        Long bookId = 1L;
        BookDto bookDto = new BookDto(bookId, "A book", "An author",
                "97834234325", new BigDecimal("100"), "no description",
                "image.url", null);
        String author = "An author";
        List<BookDto> expected = List.of(bookDto);
        String jsonRequest = objectMapper.writeValueAsString(List.of(requestDto));

        MvcResult result = mockMvc.perform(get("/books/author/" + author)
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), objectMapper.getTypeFactory()
                .constructCollectionType(List.class, BookDto.class));

        EqualsBuilder.reflectionEquals(expected, actual);
    }
}
