// src/test/java/com/users/users_ms/domain/validation/core/PasswordValidatorTest.java
package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    // PasswordValidatorTest.java
    @Test
    void constructorThrowsWrapped() throws Exception {
        Constructor<PasswordValidator> ctor = PasswordValidator.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        InvocationTargetException ite = assertThrows(InvocationTargetException.class, ctor::newInstance);
        Throwable cause = ite.getCause();
        assertTrue(cause instanceof UnsupportedOperationException);
        assertEquals("Utility class cannot be instantiated", cause.getMessage());
    }


    @Test
    void validPassword() {
        assertDoesNotThrow(() -> PasswordValidator.validate("Abcdef1!"));
    }

    @Test
    void emptyPassword() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
            () -> PasswordValidator.validate(""));
        assertEquals(ExceptionMessages.PASSWORD_EMPTY, ex.getMessage());
    }

    @Test
    void missingUppercase() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
            () -> PasswordValidator.validate("abcdef1!"));
        assertEquals(ExceptionMessages.PASSWORD_MISSING_UPPERCASE, ex.getMessage());
    }

    @Test
    void missingLowercase() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
            () -> PasswordValidator.validate("ABCDEF1!"));
        assertEquals(ExceptionMessages.PASSWORD_MISSING_LOWERCASE, ex.getMessage());
    }

    @Test
    void missingDigit() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
            () -> PasswordValidator.validate("Abcdefg!"));
        assertEquals(ExceptionMessages.PASSWORD_MISSING_NUMBER, ex.getMessage());
    }

    @Test
    void missingSymbol() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
            () -> PasswordValidator.validate("Abcdefg1"));
        assertEquals(ExceptionMessages.PASSWORD_MISSING_SYMBOL, ex.getMessage());
    }

    @Test
    void leadingSpace() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
            () -> PasswordValidator.validate(" Abcdef1!"));
        assertEquals(ExceptionMessages.PASSWORD_SPACES, ex.getMessage());
    }

    @Test
    void trailingSpace() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
            () -> PasswordValidator.validate("Abcdef1! "));
        assertEquals(ExceptionMessages.PASSWORD_SPACES, ex.getMessage());
    }
}
