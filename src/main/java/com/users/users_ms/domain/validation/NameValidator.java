package com.users.users_ms.domain.validation;

import com.users.users_ms.commons.constants.ExceptionMessages;

public class NameValidator {
    private NameValidator() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void validate(String name) {

        if (!name.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
            throw new IllegalArgumentException(ExceptionMessages.NAME_INVALID);
        }
        if (name.contains("  ")) {
            throw new IllegalArgumentException(ExceptionMessages.NAME_DOUBLE_SPACE);
        }
        if (name.startsWith(" ") || name.endsWith(" ")) {
            throw new IllegalArgumentException(ExceptionMessages.NAME_TRIM_ERROR);
        }
        if (name.length() < 2) {
            throw new IllegalArgumentException(ExceptionMessages.NAME_TOO_SHORT);
        }
    }
}

