// src/test/java/com/users/users_ms/domain/validation/PhoneValidatorTest.java

package com.users.users_ms.domain.validation;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class PhoneValidatorTest {

    @Test
    void validate_ShouldThrowException_WhenPhoneIsNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> PhoneValidator.validate(null));
        assertThrows(IllegalArgumentException.class, () -> PhoneValidator.validate(""));
    }

    @Test
    void validate_ShouldThrowException_WhenPlusSignMisplaced() {
        assertThrows(IllegalArgumentException.class, () -> PhoneValidator.validate("123+456789"));
        assertThrows(IllegalArgumentException.class, () -> PhoneValidator.validate("+123+456789"));
    }

    @Test
    void validate_ShouldThrowException_WhenStartsWith00() {
        assertThrows(IllegalArgumentException.class, () -> PhoneValidator.validate("00123456789"));
    }

    @Test
    void validate_ShouldThrowException_WhenInvalidCharactersOrLength() {
        assertThrows(IllegalArgumentException.class, () -> PhoneValidator.validate("abc123"));
        assertThrows(IllegalArgumentException.class, () -> PhoneValidator.validate("123456"));
        assertThrows(IllegalArgumentException.class, () -> PhoneValidator.validate("12345678901234"));
    }

    @Test
    void validate_ShouldPass_WhenValidPhone() {
        assertDoesNotThrow(() -> PhoneValidator.validate("1234567890"));
        assertDoesNotThrow(() -> PhoneValidator.validate("+12345678901"));
    }

    @Test
    void constructor_ShouldThrowException() throws Exception {
        var constructor = PhoneValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        Throwable cause = exception.getCause();
        assertTrue(cause instanceof UnsupportedOperationException);
        assertEquals("Clase utilitaria, no debe instanciarse.", cause.getMessage());
    }
}
