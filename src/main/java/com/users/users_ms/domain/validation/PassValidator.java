package com.users.users_ms.domain.validation;

import com.users.users_ms.commons.constants.ExceptionMessages;

public class PassValidator {
    private PassValidator() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void validate(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.PASSWORD_EMPTY);
        }
        if (password.length() < 8 || password.length() > 64) {
            throw new IllegalArgumentException(ExceptionMessages.PASSWORD_LENGTH);
        }
        if (Character.isWhitespace(password.charAt(0)) || Character.isWhitespace(password.charAt(password.length() - 1))) {
            throw new IllegalArgumentException(ExceptionMessages.PASSWORD_SPACES);
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException(ExceptionMessages.PASSWORD_MISSING_UPPERCASE);
        }
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException(ExceptionMessages.PASSWORD_MISSING_LOWERCASE);
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException(ExceptionMessages.PASSWORD_MISSING_NUMBER);
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-={}\\[\\]|;:'\",.<>/?].*")) {
            throw new IllegalArgumentException(ExceptionMessages.PASSWORD_MISSING_SYMBOL);
        }
    }
}
