package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    @Test
    void shouldPassWithValidPassword() {
        assertDoesNotThrow(() -> PasswordValidator.validate("Valid123!"));
    }

    @Test
    void shouldThrowForEmptyPassword() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> PasswordValidator.validate(" "));
        assertEquals(ExceptionMessages.PASSWORD_EMPTY, ex.getMessage());
    }

    @Test
    void shouldThrowForShortPassword() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> PasswordValidator.validate("A1a!"));
        assertEquals(ExceptionMessages.PASSWORD_LENGTH, ex.getMessage());
    }

    @Test
    void shouldThrowForMissingUppercase() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> PasswordValidator.validate("password123!"));
        assertEquals(ExceptionMessages.PASSWORD_MISSING_UPPERCASE, ex.getMessage());
    }

    @Test
    void shouldThrowForMissingLowercase() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> PasswordValidator.validate("PASSWORD123!"));
        assertEquals(ExceptionMessages.PASSWORD_MISSING_LOWERCASE, ex.getMessage());
    }

    @Test
    void shouldThrowForMissingNumber() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> PasswordValidator.validate("Password!"));
        assertEquals(ExceptionMessages.PASSWORD_MISSING_NUMBER, ex.getMessage());
    }

    @Test
    void shouldThrowForMissingSymbol() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> PasswordValidator.validate("Password123"));
        assertEquals(ExceptionMessages.PASSWORD_MISSING_SYMBOL, ex.getMessage());
    }

    @Test
    void shouldThrowIfStartsOrEndsWithSpace() {
        Exception ex1 = assertThrows(InvalidFieldException.class,
                () -> PasswordValidator.validate(" Password123!"));
        assertEquals(ExceptionMessages.PASSWORD_SPACES, ex1.getMessage());

        Exception ex2 = assertThrows(InvalidFieldException.class,
                () -> PasswordValidator.validate("Password123! "));
        assertEquals(ExceptionMessages.PASSWORD_SPACES, ex2.getMessage());
    }
}