package com.example.bookshop.mapper;

import com.example.bookshop.config.MapperConfig;
import com.example.bookshop.dto.orderitem.OrderItemDto;
import java.util.Set;

import com.example.bookshop.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, componentModel = "spring",
        uses = OrderItemMapper.class)
public interface OrderItemListMapper {
    Set<OrderItemDto> toOrderItemDtoSet(Set<OrderItem> orderItems);

}
