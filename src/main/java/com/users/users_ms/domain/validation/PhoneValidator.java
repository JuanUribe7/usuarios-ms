package com.users.users_ms.domain.validation;


import com.users.users_ms.commons.constants.ExceptionMessages;

public class PhoneValidator {
    private PhoneValidator() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void validate(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.PHONE_EMPTY);
        }
        if (phone.indexOf('+') > 0 || phone.chars().filter(ch -> ch == '+').count() > 1) {
            throw new IllegalArgumentException(ExceptionMessages.PHONE_PLUS_ERROR);
        }
        String numberOnly = phone.startsWith("+") ? phone.substring(1) : phone;
        if (numberOnly.startsWith("00")) {
            throw new IllegalArgumentException(ExceptionMessages.PHONE_INVALID_PREFIX);
        }
        if (!numberOnly.matches("^[0-9]{10,13}$")) {
            throw new IllegalArgumentException(ExceptionMessages.PHONE_INVALID_FORMAT);
        }
    }
}

