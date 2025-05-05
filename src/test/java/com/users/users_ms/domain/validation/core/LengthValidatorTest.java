package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LengthValidatorTest {

    @Test
    void shouldPassWhenLengthIsValid() {
        assertDoesNotThrow(() -> LengthValidator.validate("12345", 2, 10, "error"));
    }

    @Test
    void shouldThrowWhenTooShort() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> LengthValidator.validate("1", 2, 10, "error"));
        assertEquals("error", ex.getMessage());
    }

    @Test
    void shouldThrowWhenTooLong() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> LengthValidator.validate("12345678901", 2, 10, "error"));
        assertEquals("error", ex.getMessage());
    }
}