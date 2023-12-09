package com.example.bookshop.controller;

import com.example.bookshop.dto.category.CategoryDto;
import com.example.bookshop.dto.category.CreateCategoryRequestDto;
import com.example.bookshop.dto.order.CreateOrderDto;
import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Create a new order",
            description = "add information to db")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderDto create(@RequestBody @Valid CreateOrderDto requestDto) {
        return orderService.save(requestDto);
    }
}
