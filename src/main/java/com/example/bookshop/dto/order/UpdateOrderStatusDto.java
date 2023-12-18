package com.example.bookshop.dto.order;

import com.example.bookshop.model.Status;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusDto(
        @NotNull
        Status status){
}
