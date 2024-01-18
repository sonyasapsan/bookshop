package com.example.bookshop.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookshop.dto.category.CategoryDto;
import com.example.bookshop.dto.category.CreateCategoryRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Sql(scripts = "classpath:database/category/category/insert-category.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/category/category/delete-from-categories.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ImportResource({"classpath*:src/test/resources/application.properties"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {
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
    @DisplayName("Save new category")
    public void create_validCase_returnCategoryDto() throws Exception {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto(
                "Comedy", "something funny"
        );
        CategoryDto expected = new CategoryDto(1L, "Comedy",
                "something funny");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), CategoryDto.class);
        EqualsBuilder.reflectionEquals(actual, expected);
    }

    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    @Test
    @DisplayName("Get the category by id")
    public void findById_validCase_returnCategoryDto() throws Exception {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto(
                "Comedy", "something funny"
        );
        Long categoryId = 1L;
        CategoryDto expected = new CategoryDto(categoryId, "Comedy",
                "something funny");
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(get("/categories/" + categoryId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), CategoryDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.id());
        Assertions.assertEquals(expected.name(), actual.name());
        Assertions.assertEquals(expected.description(), actual.description());
    }

    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    @Test
    @DisplayName("Get all categories")
    public void getAll_validCase_returnCategoryDtoList() throws Exception {
        CreateCategoryRequestDto firstRequestDto = new CreateCategoryRequestDto(
                "Comedy", "something funny"
        );
        CreateCategoryRequestDto secondRequestDto = new CreateCategoryRequestDto(
                "Horror", "something scary"
        );
        CategoryDto firstCategoryDto = new CategoryDto(1L, "Comedy",
                "something funny");
        CategoryDto secondCategoryDto = new CategoryDto(2L, "Horror",
                "something scary");
        List<CategoryDto> expected = List.of(firstCategoryDto, secondCategoryDto);
        String jsonRequest = objectMapper.writeValueAsString(List.of(
                firstRequestDto, secondRequestDto
        ));

        MvcResult result = mockMvc.perform(get("/categories")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CategoryDto> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, CategoryDto.class)
        );
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update the category by certain id")
    public void update_validCase_returnCategoryDto() throws Exception {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto(
                "Comedy", "something very funny"
        );
        Long categoryId = 1L;
        CategoryDto expected = new CategoryDto(categoryId, "Comedy",
                "something very funny");
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/categories/" + categoryId)
                        .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), CategoryDto.class);
        EqualsBuilder.reflectionEquals(actual, expected);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete a category with certain id")
    public void delete_validCase_returnStatus204() throws Exception {
        Long categoryId = 1L;
        MvcResult result = mockMvc.perform(delete("/categories/" + categoryId))
                .andReturn();
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }
}
