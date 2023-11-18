package com.example.bookshop.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(@NotBlank
                                  @Size(min = 8, max = 20)
                                  @Email
                                  String email,
                                  @NotBlank
                                  @Size(min = 8, max = 20)
                                  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
                                  String password
                                  ) {
}
