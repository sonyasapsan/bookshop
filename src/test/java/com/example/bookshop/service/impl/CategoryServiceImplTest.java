package com.example.bookshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.bookshop.dto.category.CategoryDto;
import com.example.bookshop.dto.category.CreateCategoryRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.CategoryMapper;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.category.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    @Test
    @DisplayName("Save category successfully")
    public void save_validCase_returnCategoryDto() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setName("Comedy");
        category.setDescription("something funny");
        category.setId(categoryId);
        CategoryDto expected = new CategoryDto(1L, "Comedy", "something funny");
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto(
                "Comedy", "something funny"
        );

        Mockito.when(categoryMapper.toCategory(requestDto)).thenReturn(category);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryDto actual = categoryService.save(requestDto);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Find category by certain id")
    public void findById_validCase_returnCategoryDto() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Comedy");
        category.setDescription("something funny");
        CategoryDto expected = new CategoryDto(1L, "Comedy",
                "something funny");

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryDto actual = categoryService.findById(categoryId);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Get exception because of invalid id while getting category")
    public void findById_invalidId_returnException() {
        Long categoryId = -1L;

        Mockito.when(categoryRepository.findById(categoryId)).thenThrow(
                new EntityNotFoundException("Can't find category with this id"
                + categoryId)
        );
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.findById(categoryId)
        );

        String expected = "Can't find category with this id" + categoryId;
        String actual = exception.getMessage();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update category with certain id")
    public void update_validCase_returnCategoryDto() {
        Long categoryId = 1L;
        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setName("Comedy");
        updatedCategory.setDescription("something funny");
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto(
                "Comedy", "something funny"
        );
        CategoryDto expected = new CategoryDto(categoryId, "Comedy",
                "something funny");

        Mockito.when(categoryRepository.existsById(categoryId)).thenReturn(true);
        Mockito.when(categoryMapper.toCategory(requestDto)).thenReturn(updatedCategory);
        Mockito.when(categoryRepository.save(updatedCategory)).thenReturn(updatedCategory);
        Mockito.when(categoryMapper.toDto(updatedCategory)).thenReturn(expected);

        CategoryDto actual = categoryService.update(requestDto, categoryId);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get exception because of invalid id while updating category")
    public void update_invalidId_returnException() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto(
                "Comedy", "something funny"
        );
        Long categoryId = 1L;

        Mockito.lenient().when(categoryRepository.existsById(categoryId)).thenReturn(false);
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> categoryService.update(requestDto, categoryId));

        String expected = "There is no category with such id: " + categoryId;
        String actual = exception.getMessage();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete the category by certain id")
    public void deleteById_validCase_deleted() {
        Long categoryId = 1L;
        categoryService.deletedById(categoryId);
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    @DisplayName("Get all categories")
    public void getAll_validCase_returnCategoryDtoList() {
        Long firstCategoryId = 1L;
        Category firstCategory = new Category();
        firstCategory.setId(firstCategoryId);
        firstCategory.setName("Comedy");
        firstCategory.setDescription("something funny");

        Long secondCategoryId = 1L;
        Category secondCategory = new Category();
        secondCategory.setId(secondCategoryId);
        secondCategory.setName("Horror");
        secondCategory.setDescription("something scary");

        Pageable pageable = PageRequest.of(0, 10);
        List<Category> categories = List.of(firstCategory, secondCategory);
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());

        CategoryDto firstCategoryDto = new CategoryDto(firstCategory.getId(),
                firstCategory.getName(), firstCategory.getDescription());
        CategoryDto secondCategoryDto = new CategoryDto(secondCategory.getId(),
                secondCategory.getName(), secondCategory.getDescription());

        Mockito.when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        Mockito.when(categoryMapper.toDto(firstCategory)).thenReturn(firstCategoryDto);
        Mockito.when(categoryMapper.toDto(secondCategory)).thenReturn(secondCategoryDto);

        List<CategoryDto> expected = List.of(firstCategoryDto, secondCategoryDto);
        List<CategoryDto> actual = categoryService.getAll(pageable);
        Assertions.assertEquals(expected, actual);
    }
}
