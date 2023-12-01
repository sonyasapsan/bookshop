package com.example.bookshop.mapper;

import com.example.bookshop.config.MapperConfig;
import com.example.bookshop.dto.shoppingcart.ShoppingCartDto;
import com.example.bookshop.model.ShoppingCart;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @AfterMapping
    default void setShoppingCartDetails(@MappingTarget ShoppingCartDto shoppingCartDto,
                                 ShoppingCart shoppingCart) {
        shoppingCartDto.setUserId(shoppingCart.getUser().getId());
    }
}
