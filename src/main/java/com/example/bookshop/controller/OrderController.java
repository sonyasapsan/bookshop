package com.example.bookshop.controller;

import com.example.bookshop.dto.order.CreateOrderDto;
import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.dto.order.UpdateOrderStatusDto;
import com.example.bookshop.dto.orderitem.OrderItemDto;
import com.example.bookshop.service.OrderItemService;
import com.example.bookshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Create a new order",
            description = "add information to db")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderDto create(@RequestBody @Valid CreateOrderDto requestDto) {
        return orderService.save(requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all orders",
            description = "get information from db")
    @GetMapping
    public List<OrderDto> getAllOrders(@PageableDefault Pageable pageable) {
        return orderService.getAllOrders(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "update Status",
            description = "update status for the certain order")
    @PatchMapping("/{id}")
    public OrderDto updateOrderStatus(@RequestBody @Valid UpdateOrderStatusDto updateOrderStatusDto,
                                  @PathVariable @Positive Long id) {
        return orderService.updateOrderStatus(updateOrderStatusDto, id);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get items from order",
            description = "get information from db")
    @GetMapping("/{id}/items")
    public List<OrderItemDto> getItemsFromOrder(@PathVariable @Positive Long id) {
        return orderItemService.getItemsFromOrder(id);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get item from order by id",
            description = "get information from db")
    @GetMapping("/{id}/items/{itemId}")
    public OrderItemDto getItemFromOrder(@PathVariable @Positive Long itemId,
                                         @PathVariable @Positive Long id) {
        return orderItemService.getItemFromOrderById(itemId);
    }
}
