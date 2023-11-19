package com.example.bookshop.service;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.category.CategoryDto;
import com.example.bookshop.dto.category.CreateCategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryDto save(CreateCategoryRequestDto requestDto);

    List<CategoryDto> getAll(Pageable pageable);

    CategoryDto findById(Long id);

    CategoryDto update(CreateCategoryRequestDto createCategoryRequestDto, Long id);

    void deletedById(Long id);

    List<BookDto> getAllBooksByCategory(Long id);
}
