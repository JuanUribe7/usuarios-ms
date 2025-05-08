// src/test/java/com/users/users_ms/domain/validation/core/EmptyValidatorTest.java
package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class EmptyValidatorTest {

    // EmptyValidatorTest.java
    @Test
    void constructorThrowsWrapped() throws Exception {
        Constructor<EmptyValidator> ctor = EmptyValidator.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        InvocationTargetException ite = assertThrows(InvocationTargetException.class, ctor::newInstance);
        Throwable cause = ite.getCause();
        assertTrue(cause instanceof UnsupportedOperationException);
        assertEquals("Utility class cannot be instantiated", cause.getMessage());
    }

    @Test
    void requireNonEmptyValid() {
        assertDoesNotThrow(() -> EmptyValidator.requireNonEmpty("ok", "error"));
    }

    @Test
    void requireNonEmptyFailsOnNull() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
            () -> EmptyValidator.requireNonEmpty(null, ExceptionMessages.NAME_INVALID));
        assertEquals(ExceptionMessages.NAME_INVALID, ex.getMessage());
    }

    @Test
    void requireNonEmptyFailsOnBlank() {
        assertThrows(InvalidFieldException.class,
            () -> EmptyValidator.requireNonEmpty("   ", ExceptionMessages.NAME_INVALID));
    }

    @Test
    void requireNotNullValid() {
        assertDoesNotThrow(() -> EmptyValidator.requireNotNull(new Object(), "error"));
    }

    @Test
    void requireNotNullFails() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
            () -> EmptyValidator.requireNotNull(null, ExceptionMessages.BIRTHDATE_EMPTY));
        assertEquals(ExceptionMessages.BIRTHDATE_EMPTY, ex.getMessage());
    }
}
