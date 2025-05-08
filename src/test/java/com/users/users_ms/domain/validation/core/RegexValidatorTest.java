// src/test/java/com/users/users_ms/domain/validation/core/RegexValidatorTest.java
package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class RegexValidatorTest {


    @Test
    void constructorThrowsWrapped() throws Exception {
        Constructor<RegexValidator> ctor = RegexValidator.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        InvocationTargetException ite = assertThrows(InvocationTargetException.class, ctor::newInstance);
        Throwable cause = ite.getCause();
        assertTrue(cause instanceof UnsupportedOperationException);
        assertEquals("Utility class cannot be instantiated", cause.getMessage());
    }


    @Test
    void validPatterns() {
        assertDoesNotThrow(() -> RegexValidator.validate("Juan Pérez", RegexValidator.Field.NAME, ExceptionMessages.NAME_INVALID));
        assertDoesNotThrow(() -> RegexValidator.validate("12345678", RegexValidator.Field.DOCUMENT, ExceptionMessages.DOCUMENT_NON_NUMERIC));
        assertDoesNotThrow(() -> RegexValidator.validate("a@b.com", RegexValidator.Field.EMAIL, ExceptionMessages.EMAIL_INVALID_FORMAT));
        assertDoesNotThrow(() -> RegexValidator.validate("1234567890", RegexValidator.Field.PHONE, ExceptionMessages.PHONE_INVALID_FORMAT));
    }

    @Test
    void invalidPattern() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
            () -> RegexValidator.validate("abc!", RegexValidator.Field.NAME, ExceptionMessages.NAME_INVALID));
        assertEquals(ExceptionMessages.NAME_INVALID, ex.getMessage());
    }
}
