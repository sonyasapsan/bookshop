package com.example.bookshop.mapper;

import com.example.bookshop.config.MapperConfig;
import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookToDtoWithoutCategoryIds;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoriesIds", ignore = true)
    BookDto toDto(Book book);

    Book toBook(CreateBookRequestDto createBookRequestDto);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.categoriesIds().addAll(book.getCategories().stream()
                .map(Category::getId).collect(Collectors.toSet()));
    }

    BookToDtoWithoutCategoryIds toBookWithoutCategoryIds(Book book);
}
