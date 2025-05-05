package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;

public class LengthValidator {

    private LengthValidator() {
        throw new UnsupportedOperationException(ExceptionMessages.UTILITY_CLASS_INSTANTIATION);
    }

    public static void validate(String value, int min, int max, String errorMessage) {
        if (value.length() < min || value.length() > max) {
            throw new InvalidFieldException(errorMessage);
        }
    }
}
