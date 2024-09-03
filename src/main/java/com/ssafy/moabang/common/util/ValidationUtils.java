package com.ssafy.moabang.common.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

public abstract class ValidationUtils {

    private final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validate(T command) {
        Set<ConstraintViolation<T>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
