package com.example.bookshop.dto.order;

import jakarta.validation.constraints.NotBlank;

public record CreateOrderDto(@NotBlank
                             String shippingAddress) {
}
