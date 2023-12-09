package com.example.bookshop.mapper;

import com.example.bookshop.config.MapperConfig;
import com.example.bookshop.dto.cartitem.CartItemDto;
import com.example.bookshop.dto.orderitem.OrderItemDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.OrderItem;
import com.example.bookshop.repository.book.BookRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public interface OrderItemMapper extends BookRepository {
    OrderItemDto toDto(@MappingTarget OrderItemDto orderItemDto,
                       OrderItem orderItem);

    OrderItem fromCartItemToOrderItem(CartItemDto cartItem);


}
