package com.users.users_ms.domain.validation;

import com.users.users_ms.commons.constants.ExceptionMessages;

public class DocumentValidator {
    private DocumentValidator() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void validate(String document) {
        if (document == null || document.trim().isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.DOCUMENT_EMPTY);
        }
        if (!document.matches("^\\d+$")) {
            throw new IllegalArgumentException(ExceptionMessages.DOCUMENT_NON_NUMERIC);
        }
        if (document.length() < 8 || document.length() > 11) {
            throw new IllegalArgumentException(ExceptionMessages.DOCUMENT_LENGTH);
        }
        if (document.startsWith("0")) {
            throw new IllegalArgumentException(ExceptionMessages.DOCUMENT_INVALID_START);
        }
    }
}
