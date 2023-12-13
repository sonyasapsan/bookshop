package com.example.bookshop.mapper;

import com.example.bookshop.config.MapperConfig;
import com.example.bookshop.dto.orderitem.OrderItemDto;
import com.example.bookshop.model.OrderItem;
import java.util.Set;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class,
        uses = OrderItemMapper.class)
public interface OrderItemListMapper {
    Set<OrderItemDto> toOrderItemDtoSet(Set<OrderItem> orderItems);
}
