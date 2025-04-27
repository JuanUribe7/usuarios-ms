// src/test/java/com/users/users_ms/domain/validation/PassValidatorTest.java

package com.users.users_ms.domain.validation;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class PassValidatorTest {

    @Test
    void validate_ShouldThrowException_WhenPasswordIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PassValidator.validate(null));
        assertEquals("La clave no puede estar vacía", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenPasswordIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PassValidator.validate("   "));
        assertEquals("La clave no puede estar vacía", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenPasswordIsTooShort() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PassValidator.validate("Ab1!"));
        assertEquals("La clave debe tener entre 8 y 64 caracteres", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenPasswordIsTooLong() {
        String longPassword = "A".repeat(65) + "a1!";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PassValidator.validate(longPassword));
        assertEquals("La clave debe tener entre 8 y 64 caracteres", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenPasswordHasLeadingOrTrailingSpaces() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PassValidator.validate(" Abcdef1!"));
        assertEquals("La clave no puede tener espacios al inicio o al final", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenPasswordLacksUppercase() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PassValidator.validate("abcdef1!"));
        assertEquals("La clave debe contener al menos una letra mayúscula", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenPasswordLacksLowercase() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PassValidator.validate("ABCDEF1!"));
        assertEquals("La clave debe contener al menos una letra minúscula", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenPasswordLacksNumber() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PassValidator.validate("Abcdefg!"));
        assertEquals("La clave debe contener al menos un número", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenPasswordLacksSpecialCharacter() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PassValidator.validate("Abcdefg1"));
        assertEquals("La clave debe contener al menos un carácter especial", exception.getMessage());
    }

    @Test
    void validate_ShouldPass_WhenPasswordIsValid() {
        assertDoesNotThrow(() -> PassValidator.validate("Abcd1234!"));
    }

    @Test
    void constructor_ShouldThrowException() throws Exception {
        var constructor = PassValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        try {
            constructor.newInstance();
            fail("Se esperaba UnsupportedOperationException");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof UnsupportedOperationException);
            assertEquals("Clase utilitaria, no debe instanciarse.", e.getCause().getMessage());
        }
    }
}
