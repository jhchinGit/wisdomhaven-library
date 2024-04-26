package com.wisdomhaven.library.borrowing.util;

import jakarta.validation.*;

import java.util.HashSet;
import java.util.Set;

public final class RequestUtil {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    private RequestUtil() {}

    public static <T> void validate(T... requests) {
        Set<ConstraintViolation<T>> violations = new HashSet<>();

        for (T request : requests) {
            violations.addAll(validator.validate(request));
        }

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
