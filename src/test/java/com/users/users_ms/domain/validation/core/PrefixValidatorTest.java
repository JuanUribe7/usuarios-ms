// src/test/java/com/users/users_ms/domain/validation/core/PrefixValidatorTest.java
package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class PrefixValidatorTest {

    @Test
    void constructorThrowsWrapped() throws Exception {
        Constructor<PrefixValidator> ctor = PrefixValidator.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        InvocationTargetException ite = assertThrows(InvocationTargetException.class, ctor::newInstance);
        Throwable cause = ite.getCause();
        assertTrue(cause instanceof UnsupportedOperationException);
        assertEquals("Utility class cannot be instantiated", cause.getMessage());
    }


    @Test
    void mustNotStartWith() {
        assertThrows(InvalidFieldException.class,
            () -> PrefixValidator.mustNotStartWith("abc", "a", ExceptionMessages.DOCUMENT_INVALID_START));
        assertDoesNotThrow(() ->
            PrefixValidator.mustNotStartWith("abc", "b", "err"));
    }

    @Test
    void mustNotHaveDoubleSpaces() {
        assertThrows(InvalidFieldException.class,
            () -> PrefixValidator.mustNotHaveDoubleSpaces("a  b", ExceptionMessages.NAME_DOUBLE_SPACE));
        assertDoesNotThrow(() ->
            PrefixValidator.mustNotHaveDoubleSpaces("a b", "err"));
    }

    @Test
    void mustNotHaveTrimSpaces() {
        assertThrows(InvalidFieldException.class,
            () -> PrefixValidator.mustNotHaveTrimSpaces(" a", ExceptionMessages.NAME_TRIM_ERROR));
        assertThrows(InvalidFieldException.class,
            () -> PrefixValidator.mustNotHaveTrimSpaces("a ", ExceptionMessages.NAME_TRIM_ERROR));
        assertDoesNotThrow(() ->
            PrefixValidator.mustNotHaveTrimSpaces("a", "err"));
    }

    @Test
    void mustNotContain() {
        assertThrows(InvalidFieldException.class,
            () -> PrefixValidator.mustNotContain("hello", "ll", "err"));
        assertDoesNotThrow(() ->
            PrefixValidator.mustNotContain("hello", "xx", "err"));
    }
}
