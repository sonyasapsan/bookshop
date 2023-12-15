package com.example.bookshop.service.impl;

import com.example.bookshop.dto.orderitem.OrderItemDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.OrderItemMapper;
import com.example.bookshop.model.CartItem;
import com.example.bookshop.model.Order;
import com.example.bookshop.model.OrderItem;
import com.example.bookshop.repository.cartitem.CartItemRepository;
import com.example.bookshop.repository.orderitem.OrderItemRepository;
import com.example.bookshop.service.OrderItemService;
import com.example.bookshop.service.ShoppingCartService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final ShoppingCartService shoppingCartService;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Set<OrderItem> getOrderItems(Order order) {
        return cartItemRepository.findAllByShoppingCartId(shoppingCartService
                .getShoppingCart().id()).stream()
                .map(this::getOrderItemFromCart)
                .peek(o -> o.setOrder(order))
                .collect(Collectors.toSet());
    }

    @Override
    public List<OrderItemDto> getItemsFromOrder(Long id) {
        return orderItemRepository.getAllByOrderId(id).stream()
                .map(orderItemMapper::toOrderItemDto)
                .toList();
    }

    @Override
    public OrderItemDto getItemFromOrderById(Long id) {
        return orderItemMapper.toOrderItemDto(orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order "
                        + "item by this id: " + id)));
    }

    private OrderItem getOrderItemFromCart(CartItem cartItem) {
        OrderItem orderItem = orderItemMapper.convertCartItemToOrderItem(cartItem);
        orderItem.setPrice(orderItem.getBook().getPrice()
                .multiply(new BigDecimal(orderItem.getQuantity())));
        return orderItem;
    }
}
