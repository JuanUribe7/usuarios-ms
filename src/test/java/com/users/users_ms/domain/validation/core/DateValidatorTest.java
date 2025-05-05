package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateValidatorTest {

    @Test
    void shouldThrowWhenDateIsNull() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> DateValidator.validateAdultBirthDate(null));
        assertEquals(ExceptionMessages.BIRTHDATE_EMPTY, ex.getMessage());
    }

    @Test
    void shouldThrowWhenDateInFuture() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> DateValidator.validateAdultBirthDate(LocalDate.now().plusDays(1)));
        assertEquals(ExceptionMessages.BIRTHDATE_FUTURE, ex.getMessage());
    }

    @Test
    void shouldThrowWhenDateTooOld() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> DateValidator.validateAdultBirthDate(LocalDate.of(1800, 1, 1)));
        assertEquals(ExceptionMessages.BIRTHDATE_TOO_OLD, ex.getMessage());
    }

    @Test
    void shouldThrowWhenUserIsNotAdult() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> DateValidator.validateAdultBirthDate(LocalDate.now().minusYears(17)));
        assertEquals(ExceptionMessages.BIRTHDATE_NOT_ADULT, ex.getMessage());
    }

    @Test
    void shouldPassWhenDateIsValid() {
        assertDoesNotThrow(() -> DateValidator.validateAdultBirthDate(LocalDate.now().minusYears(20)));
    }
}