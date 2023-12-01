package com.example.bookshop.validation.field.match.validator;

import com.example.bookshop.exception.RegistrationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        Object firstObj;
        Object secondObj;
        try {
            Field firstField = value.getClass().getDeclaredField(firstFieldName);
            firstField.setAccessible(true);
            firstObj = firstField.get(value);
            Field secondField = value.getClass().getDeclaredField(secondFieldName);
            secondField.setAccessible(true);
            secondObj = secondField.get(value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RegistrationException("Something went wrong while registration");
        }
        return firstObj != null && firstObj.equals(secondObj);
    }

    public String getMessage() {
        return message;
    }
}
