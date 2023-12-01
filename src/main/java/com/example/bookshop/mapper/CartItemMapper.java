package com.example.bookshop.mapper;

import com.example.bookshop.config.MapperConfig;
import com.example.bookshop.dto.cartitem.CartItemDto;
import com.example.bookshop.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookshop.model.CartItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    CartItemDto toDto(CartItem cartItem);

    CartItem toCartItem(CreateCartItemRequestDto createCartItemRequestDto);

    @AfterMapping
    default void setBookDetails(@MappingTarget CartItemDto cartItemDto,
                           CartItem cartItem) {
        cartItemDto.setBookId(cartItem.getBook().getId());
        cartItemDto.setBookTitle(cartItem.getBook().getTitle());
    }
}
