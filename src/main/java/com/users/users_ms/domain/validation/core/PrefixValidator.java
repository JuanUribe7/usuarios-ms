package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;

public class PrefixValidator {

    private PrefixValidator() {
        throw new UnsupportedOperationException(ExceptionMessages.UTILITY_CLASS_INSTANTIATION);
    }

    public static void mustNotStartWith(String value, String prefix, String errorMessage) {
        if (value.startsWith(prefix)) {
            throw new InvalidFieldException(errorMessage);
        }
    }

    public static void mustNotHaveDoubleSpaces(String value, String errorMessage) {
        if (value.contains("  ")) {
            throw new InvalidFieldException(errorMessage);
        }
    }

    public static void mustNotHaveTrimSpaces(String value, String errorMessage) {
        if (value.startsWith(" ") || value.endsWith(" ")) {
            throw new InvalidFieldException(errorMessage);
        }
    }

    public static void mustNotContain(String value, String target, String errorMessage) {
        if (value.contains(target)) {
            throw new InvalidFieldException(errorMessage);
        }
    }
}
