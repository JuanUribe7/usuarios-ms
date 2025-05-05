package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmptyValidatorTest {

    @Test
    void requireNonEmpty_shouldThrowForNull() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> EmptyValidator.requireNonEmpty(null, "error"));
        assertEquals("error", ex.getMessage());
    }

    @Test
    void requireNonEmpty_shouldThrowForEmpty() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> EmptyValidator.requireNonEmpty("   ", "error"));
        assertEquals("error", ex.getMessage());
    }

    @Test
    void requireNonEmpty_shouldPassForValidString() {
        assertDoesNotThrow(() -> EmptyValidator.requireNonEmpty("valid", "error"));
    }

    @Test
    void requireNotNull_shouldThrowForNull() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> EmptyValidator.requireNotNull(null, "error"));
        assertEquals("error", ex.getMessage());
    }

    @Test
    void requireNotNull_shouldPassForObject() {
        assertDoesNotThrow(() -> EmptyValidator.requireNotNull(new Object(), "error"));
    }
}