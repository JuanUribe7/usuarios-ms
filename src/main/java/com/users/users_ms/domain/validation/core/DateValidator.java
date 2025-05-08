package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;

import java.time.LocalDate;

public class DateValidator {

    private DateValidator() {
        throw new UnsupportedOperationException(ExceptionMessages.UTILITY_CLASS_INSTANTIATION);
    }

    public static void validateAdultBirthDate(LocalDate birthDate) {
        EmptyValidator.requireNotNull(birthDate, ExceptionMessages.BIRTHDATE_EMPTY);

        LocalDate today = LocalDate.now();

        if (birthDate.isAfter(today)) {
            throw new InvalidFieldException(ExceptionMessages.BIRTHDATE_FUTURE);
        }

        if (birthDate.isBefore(LocalDate.of(1900, 1, 1))) {
            throw new InvalidFieldException(ExceptionMessages.BIRTHDATE_TOO_OLD);
        }

        if (birthDate.plusYears(18).isAfter(today)) {
            throw new InvalidFieldException(ExceptionMessages.BIRTHDATE_NOT_ADULT);
        }
    }
}
