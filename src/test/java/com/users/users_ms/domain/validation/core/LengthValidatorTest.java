// src/test/java/com/users/users_ms/domain/validation/core/LengthValidatorTest.java
package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class LengthValidatorTest {

    // LengthValidatorTest.java
    @Test
    void constructorThrowsWrapped() throws Exception {
        Constructor<LengthValidator> ctor = LengthValidator.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        InvocationTargetException ite = assertThrows(InvocationTargetException.class, ctor::newInstance);
        Throwable cause = ite.getCause();
        assertTrue(cause instanceof UnsupportedOperationException);
        assertEquals("Utility class cannot be instantiated", cause.getMessage());
    }


    @Test
    void validLength() {
        assertDoesNotThrow(() -> LengthValidator.validate("abcd", 2, 10, "err"));
    }

    @Test
    void tooShort() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
            () -> LengthValidator.validate("a", 2, 10, ExceptionMessages.NAME_TOO_SHORT));
        assertEquals(ExceptionMessages.NAME_TOO_SHORT, ex.getMessage());
    }

    @Test
    void tooLong() {
        String s = "x".repeat(11);
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
            () -> LengthValidator.validate(s, 2, 10, ExceptionMessages.PASSWORD_LENGTH));
        assertEquals(ExceptionMessages.PASSWORD_LENGTH, ex.getMessage());
    }
}
