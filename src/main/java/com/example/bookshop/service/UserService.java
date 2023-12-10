package com.example.bookshop.service;

import com.example.bookshop.dto.user.UserRegistrationRequestDto;
import com.example.bookshop.dto.user.UserRegistrationResponseDto;
import com.example.bookshop.exception.RegistrationException;
import com.example.bookshop.model.User;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException;

    User getUserFromContext();
}
