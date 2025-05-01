package com.users.users_ms.domain.validation;


import com.users.users_ms.commons.constants.ExceptionMessages;


import java.time.LocalDate;

public class AgeValidator {
    private AgeValidator() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void validate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException(ExceptionMessages.BIRTHDATE_EMPTY);
        }
        LocalDate today = LocalDate.now();
        if (birthDate.isAfter(today)) {
            throw new IllegalArgumentException(ExceptionMessages.BIRTHDATE_FUTURE);
        }
        if (birthDate.isBefore(LocalDate.of(1900, 1, 1))) {
            throw new IllegalArgumentException(ExceptionMessages.BIRTHDATE_TOO_OLD);
        }
        if (birthDate.plusYears(18).isAfter(today)) {
            throw new IllegalArgumentException(ExceptionMessages.BIRTHDATE_NOT_ADULT);
        }
    }
}
