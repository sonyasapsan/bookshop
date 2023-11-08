package com.example.bookshop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {
    private static final Pattern PATTERN_OF_ISBN = Pattern.compile("^(978|979)\\d{10}$");

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        return isbn != null && PATTERN_OF_ISBN.matcher(isbn).matches();
    }
}
