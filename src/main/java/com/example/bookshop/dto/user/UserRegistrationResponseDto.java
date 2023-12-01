package com.example.bookshop.dto.user;

public record UserRegistrationResponseDto(Long id,
                                          String email,
                                          String firstName,
                                          String lastName,
                                          String shippingAddress) {
}
