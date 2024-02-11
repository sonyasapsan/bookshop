package com.example.bookshop.mapper;

import com.example.bookshop.config.MapperConfig;
import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookToDtoWithoutCategoryIds;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoriesIds", source = "categories", qualifiedByName = "setCategoryIds")
    BookDto toDto(Book book);

    Book toBook(CreateBookRequestDto createBookRequestDto);

    @Named("setCategoryIds")
    default Set<Long> getIdsFromCategories(Set<Category> categories) {
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }

    BookToDtoWithoutCategoryIds toBookWithoutCategoryIds(Book book);
}
