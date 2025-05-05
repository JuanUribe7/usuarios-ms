package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;

public class RegexValidator {

    public enum Field {
        NAME, DOCUMENT, EMAIL, PHONE
    }

    private RegexValidator() {
        throw new UnsupportedOperationException(ExceptionMessages.UTILITY_CLASS_INSTANTIATION);
    }

    public static void validate(String value, Field field, String errorMessage) {
        String regex = switch (field) {
            case NAME -> "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$";
            case DOCUMENT -> "^\\d+$";
            case EMAIL -> "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            case PHONE -> "^[0-9]{10,13}$";
        };

        if (!value.matches(regex)) {
            throw new InvalidFieldException(errorMessage);
        }
    }
}
