package com.example.bookshop.service.impl;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.category.CategoryDto;
import com.example.bookshop.dto.category.CreateCategoryRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.mapper.CategoryMapper;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.book.BookRepository;
import com.example.bookshop.repository.category.CategoryRepository;
import com.example.bookshop.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public CategoryDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryMapper.toCategory(requestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() ->
                         new EntityNotFoundException("Can't find category with this id" + id));
    }

    @Override
    public CategoryDto update(CreateCategoryRequestDto createCategoryRequestDto, Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("There is no book with such id: " + id);
        }
        Category category = categoryMapper.toCategory(createCategoryRequestDto);
        category.setId(id);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deletedById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<BookDto> getAllBooksByCategory(Long id) {
        return bookRepository.findAllByCategoriesId(id)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
