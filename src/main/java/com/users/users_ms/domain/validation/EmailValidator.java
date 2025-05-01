package com.users.users_ms.domain.validation;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.domain.ports.out.UserPersistencePort;

public class EmailValidator {
    private EmailValidator() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void validate(String email, UserPersistencePort port) {
        if (port.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException(ExceptionMessages.EMAIL_ALREADY_REGISTERED);
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.EMAIL_EMPTY);
        }
        if (email.length() > 254) {
            throw new IllegalArgumentException(ExceptionMessages.EMAIL_TOO_LONG);
        }
        if (email.contains(" ")) {
            throw new IllegalArgumentException(ExceptionMessages.EMAIL_SPACES);
        }

        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException(ExceptionMessages.EMAIL_INVALID_FORMAT);
        }

        String domain = email.substring(email.indexOf('@') + 1);
        for (String part : domain.split("\\.")) {
            if (part.startsWith("-") || part.endsWith("-")) {
                throw new IllegalArgumentException(ExceptionMessages.EMAIL_DOMAIN_INVALID);
            }
        }
    }
}
