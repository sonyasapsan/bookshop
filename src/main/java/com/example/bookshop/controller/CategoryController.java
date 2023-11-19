package com.example.bookshop.controller;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.category.CategoryDto;
import com.example.bookshop.dto.category.CreateCategoryRequestDto;
import com.example.bookshop.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category management", description = "Endpoints for managing categories")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new category",
            description = "add information to db")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryDto create(@RequestBody @Valid CreateCategoryRequestDto requestDto) {
        return categoryService.save(requestDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Get all categories",
            description = "amount can be limited by parameter")
    @GetMapping
    public List<CategoryDto> getAll(@PageableDefault Pageable pageable) {
        return categoryService.getAll(pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Find the certain category by id",
            description = "searching the certain category")
    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable @Positive Long id) {
        return categoryService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update the certain category",
            description = "update the certain category")
    @PutMapping("/{id}")
    public CategoryDto update(@RequestBody @Valid CreateCategoryRequestDto requestDto,
                              @PathVariable @Positive Long id) {
        return categoryService.update(requestDto, id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete the certain category",
            description = "Delete the category from DB by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive Long id) {
        categoryService.deletedById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "get all books by category",
            description = "getting all books by specific category")
    @GetMapping("/{id}/books")
    public List<BookDto> getAllBooksByCategory(@PathVariable @Positive Long id) {
        return categoryService.getAllBooksByCategory(id);
    }
}
