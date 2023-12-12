package com.example.bookshop.dto.order;

import com.example.bookshop.model.Status;

public record UpdateOrderStatusDto(
        Status status){
}
