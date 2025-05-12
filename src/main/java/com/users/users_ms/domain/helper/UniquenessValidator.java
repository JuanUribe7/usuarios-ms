package com.users.users_ms.domain.helper;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.AlreadyExistsException;

public class UniquenessValidator {

    UniquenessValidator() {
        throw new UnsupportedOperationException(ExceptionMessages.UTILITY_CLASS_INSTANTIATION);
    }

    public static void validate(ExistenceCheck existenceCheck, String fieldName, String value) {
        if (existenceCheck.exists()) {
            throw new AlreadyExistsException(ExceptionMessages.VALUE_ALREADY_EXISTS + fieldName + ": " + value);
        }
    }

    @FunctionalInterface
    public interface ExistenceCheck {
        boolean exists();
    }
}