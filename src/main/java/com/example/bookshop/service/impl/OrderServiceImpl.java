package com.example.bookshop.service.impl;

import com.example.bookshop.dto.order.CreateOrderDto;
import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.dto.order.UpdateOrderStatusDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.OrderMapper;
import com.example.bookshop.model.Order;
import com.example.bookshop.model.OrderItem;
import com.example.bookshop.model.Status;
import com.example.bookshop.repository.order.OrderRepository;
import com.example.bookshop.service.OrderItemService;
import com.example.bookshop.service.OrderService;
import com.example.bookshop.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final OrderMapper orderMapper;
    private final OrderItemService orderItemService;
    private final OrderRepository orderRepository;

    @Override
    public OrderDto save(CreateOrderDto requestDto) {
        Order order = setOrderDetails(requestDto);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderDto> getAllOrders(Pageable pageable) {
        return orderRepository.getAllByUser(userService.getUserFromContext(),
                pageable).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto updateOrderStatus(UpdateOrderStatusDto updateOrderStatusDto, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order by id" + id));
        order.setStatus(updateOrderStatusDto.status());
        return orderMapper.toDto(orderRepository.save(order));
    }

    private BigDecimal getTotal(Order order) {
        return new BigDecimal(String.valueOf(order.getOrderItems().stream()
                .map(OrderItem::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .sum()));
    }

    private Order setOrderDetails(CreateOrderDto requestDto) {
        Order order = orderMapper.toOrder(requestDto);
        order.setUser(userService.getUserFromContext());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.PROCESSING);
        order.setOrderItems(orderItemService.getOrderItemsByCart(order));
        order.setTotal(getTotal(order));
        return order;
    }
}
