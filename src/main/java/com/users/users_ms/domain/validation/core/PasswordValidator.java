package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;

public class PasswordValidator {

    private PasswordValidator() {
        throw new UnsupportedOperationException(ExceptionMessages.UTILITY_CLASS_INSTANTIATION);
    }

    public static void validate(String password) {
        EmptyValidator.requireNonEmpty(password, ExceptionMessages.PASSWORD_EMPTY);

        LengthValidator.validate(password, 8, 64, ExceptionMessages.PASSWORD_LENGTH);

        if (Character.isWhitespace(password.charAt(0)) || Character.isWhitespace(password.charAt(password.length() - 1))) {
            throw new InvalidFieldException(ExceptionMessages.PASSWORD_SPACES);
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new InvalidFieldException(ExceptionMessages.PASSWORD_MISSING_UPPERCASE);
        }

        if (!password.matches(".*[a-z].*")) {
            throw new InvalidFieldException(ExceptionMessages.PASSWORD_MISSING_LOWERCASE);
        }

        if (!password.matches(".*\\d.*")) {
            throw new InvalidFieldException(ExceptionMessages.PASSWORD_MISSING_NUMBER);
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-={}\\[\\]|;:'\",.<>/?].*")) {
            throw new InvalidFieldException(ExceptionMessages.PASSWORD_MISSING_SYMBOL);
        }
    }
}
