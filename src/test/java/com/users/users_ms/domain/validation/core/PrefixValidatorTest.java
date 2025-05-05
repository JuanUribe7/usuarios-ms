package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrefixValidatorTest {

    @Test
    void mustNotStartWith_shouldThrow() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> PrefixValidator.mustNotStartWith("testValue", "test", "error"));
        assertEquals("error", ex.getMessage());
    }

    @Test
    void mustNotStartWith_shouldPass() {
        assertDoesNotThrow(() -> PrefixValidator.mustNotStartWith("value", "test", "error"));
    }

    @Test
    void mustNotHaveDoubleSpaces_shouldThrow() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> PrefixValidator.mustNotHaveDoubleSpaces("has  double", "error"));
        assertEquals("error", ex.getMessage());
    }

    @Test
    void mustNotHaveDoubleSpaces_shouldPass() {
        assertDoesNotThrow(() -> PrefixValidator.mustNotHaveDoubleSpaces("valid space", "error"));
    }

    @Test
    void mustNotHaveTrimSpaces_shouldThrow() {
        Exception ex1 = assertThrows(InvalidFieldException.class,
                () -> PrefixValidator.mustNotHaveTrimSpaces(" trim", "error"));
        Exception ex2 = assertThrows(InvalidFieldException.class,
                () -> PrefixValidator.mustNotHaveTrimSpaces("trim ", "error"));
        assertEquals("error", ex1.getMessage());
        assertEquals("error", ex2.getMessage());
    }

    @Test
    void mustNotContain_shouldThrow() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> PrefixValidator.mustNotContain("contains space", " ", "error"));
        assertEquals("error", ex.getMessage());
    }

    @Test
    void mustNotContain_shouldPass() {
        assertDoesNotThrow(() -> PrefixValidator.mustNotContain("valid", " ", "error"));
    }
}