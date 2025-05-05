package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegexValidatorTest {

    @Test
    void validate_shouldPassWithValidFields() {
        assertDoesNotThrow(() -> RegexValidator.validate("Juan Pérez", RegexValidator.Field.NAME, "error"));
        assertDoesNotThrow(() -> RegexValidator.validate("12345678", RegexValidator.Field.DOCUMENT, "error"));
        assertDoesNotThrow(() -> RegexValidator.validate("test@mail.com", RegexValidator.Field.EMAIL, "error"));
        assertDoesNotThrow(() -> RegexValidator.validate("3001234567", RegexValidator.Field.PHONE, "error"));
    }

    @Test
    void validate_shouldThrowForInvalidFields() {
        assertThrows(InvalidFieldException.class,
                () -> RegexValidator.validate("Juan123", RegexValidator.Field.NAME, "error"));
        assertThrows(InvalidFieldException.class,
                () -> RegexValidator.validate("docA123", RegexValidator.Field.DOCUMENT, "error"));
        assertThrows(InvalidFieldException.class,
                () -> RegexValidator.validate("invalid.com", RegexValidator.Field.EMAIL, "error"));
        assertThrows(InvalidFieldException.class,
                () -> RegexValidator.validate("1234", RegexValidator.Field.PHONE, "error"));
    }
}