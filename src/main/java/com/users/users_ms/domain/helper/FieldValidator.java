package com.users.users_ms.domain.helper;


import com.users.users_ms.commons.exceptions.InvalidFieldException;

public class FieldValidator {

    public static void validateNotBlank(String field, String errorMessage) {
        if (field == null || field.isBlank()) {
            throw new InvalidFieldException(errorMessage) {
            };
        }
    }

    public static void validatePattern(String field, String regex, String errorMessage) {
        if (!field.matches(regex)) {
            throw new InvalidFieldException(errorMessage) {
            };
        }
    }

    public static void validateMaxLength(String field, int maxLength, String errorMessage) {
        if (field.length() > maxLength) {
            throw new InvalidFieldException(errorMessage) {
            };
        }
    }

    public static void validateMinLength(String field, int minLength, String errorMessage) {
        if (field.length() < minLength) {
            throw new InvalidFieldException(errorMessage) {
            };
        }
    }
}