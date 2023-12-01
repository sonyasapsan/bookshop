package com.example.bookshop.controller;

import com.example.bookshop.dto.cartitem.UpdateCartItemRequestDto;
import com.example.bookshop.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cart item management", description = "Endpoints for managing cart items")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "update a cart item",
                    description = "update the certain cart item")
    @PutMapping("/{id}")
    public void updateCartItem(@RequestBody @Valid UpdateCartItemRequestDto requestDto,
                               @PathVariable @Positive Long id) {
        cartItemService.update(requestDto, id);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Delete the certain cart item",
            description = "Delete the cart item from DB by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive Long id) {
        cartItemService.delete(id);
    }
}
