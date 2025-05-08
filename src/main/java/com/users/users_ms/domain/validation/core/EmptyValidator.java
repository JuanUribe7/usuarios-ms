package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;

public class EmptyValidator {

    private EmptyValidator() {
        throw new UnsupportedOperationException(ExceptionMessages.UTILITY_CLASS_INSTANTIATION);
    }

    public static void requireNonEmpty(String value, String errorMessage) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidFieldException(errorMessage);
        }
    }

    public static void requireNotNull(Object value, String errorMessage) {
        if (value == null) {
            throw new InvalidFieldException(errorMessage);
        }
    }
}
