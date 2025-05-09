package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.AlreadyExistsException;

import java.util.function.Supplier;

public class UniquenessValidator {

    private UniquenessValidator() {
        throw new UnsupportedOperationException(ExceptionMessages.UTILITY_CLASS_INSTANTIATION);
    }

    public static void validate(Supplier<Boolean> existenceCheck, String fieldName, String value) {
        if (existenceCheck.get()) {
            throw new AlreadyExistsException(ExceptionMessages.VALUE_ALREADY_EXISTS + fieldName + ": " + value);
        }
    }
}
