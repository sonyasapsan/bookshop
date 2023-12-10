package com.example.bookshop.mapper;

import com.example.bookshop.config.MapperConfig;
import com.example.bookshop.dto.cartitem.CartItemDto;
import com.example.bookshop.model.CartItem;
import java.util.Set;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, componentModel = "spring",
        uses = CartItemMapper.class)
public interface CartItemListMapper {
    Set<CartItemDto> toCartItemDtoSet(Set<CartItem> cartItemSet);
}
