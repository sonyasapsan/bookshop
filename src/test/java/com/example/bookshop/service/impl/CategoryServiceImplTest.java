package com.example.bookshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.bookshop.dto.category.CategoryDto;
import com.example.bookshop.dto.category.CreateCategoryRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.CategoryMapper;
import com.example.bookshop.mapper.impl.CategoryMapperImpl;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.category.CategoryRepository;
import java.util.List;
import java.util.Optional;
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
class CategoryServiceImplTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Spy
    private CategoryRepository categoryRepository;
    @Spy
    private CategoryMapper categoryMapper = new CategoryMapperImpl();

    @Test
    @DisplayName("Save category successfully")
    public void save_validCase_returnCategoryDto() {
        Category category = getCategory();
        CategoryDto expected = getCategoryDto();
        CreateCategoryRequestDto requestDto = getRequestDto();
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);
        CategoryDto actual = categoryService.save(requestDto);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Find category by certain id")
    public void findById_validCase_returnCategoryDto() {
        Long categoryId = 1L;
        Category category = getCategory();
        CategoryDto expected = getCategoryDto();
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
        Category updatedCategory = getCategory();
        CreateCategoryRequestDto requestDto = getRequestDto();
        CategoryDto expected = getCategoryDto();

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
        CreateCategoryRequestDto requestDto = getRequestDto();
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
        Pageable pageable = PageRequest.of(0, 10);
        List<Category> categories = getCategoryList();
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());

        Mockito.when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);

        List<CategoryDto> expected = getCategoryDtoList();
        List<CategoryDto> actual = categoryService.getAll(pageable);
        Assertions.assertEquals(expected, actual);
    }

    private static Category getCategory() {
        Category category = new Category();
        category.setName("Comedy");
        category.setDescription("something funny");
        category.setId(1L);
        return category;
    }

    private static CategoryDto getCategoryDto() {
        return new CategoryDto(1L, "Comedy", "something funny");
    }

    private static CreateCategoryRequestDto getRequestDto() {
        return new CreateCategoryRequestDto("Comedy", "something funny");
    }

    private static List<Category> getCategoryList() {
        Category firstCategory = new Category();
        firstCategory.setId(1L);
        firstCategory.setName("Comedy");
        firstCategory.setDescription("something funny");

        Category secondCategory = new Category();
        secondCategory.setId(2L);
        secondCategory.setName("Horror");
        secondCategory.setDescription("something scary");

        return List.of(firstCategory, secondCategory);
    }

    private static List<CategoryDto> getCategoryDtoList() {
        CategoryDto firstCategoryDto = new CategoryDto(1L, "Comedy", "something funny");
        CategoryDto secondCategoryDto = new CategoryDto(2L, "Horror", "something scary");
        return List.of(firstCategoryDto, secondCategoryDto);
    }
}
