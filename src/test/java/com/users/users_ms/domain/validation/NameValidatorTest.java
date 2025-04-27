// src/test/java/com/users/users_ms/domain/validation/NameValidatorTest.java

package com.users.users_ms.domain.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameValidatorTest {

    @Test
    void validate_ShouldThrowException_WhenNameContainsInvalidCharacters() {
        String name = "Juan123";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                NameValidator.validate(name));
        assertEquals("El nombre solo puede contener letras y espacios", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenNameContainsDoubleSpaces() {
        String name = "Juan  Perez";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                NameValidator.validate(name));
        assertEquals("El nombre no puede tener espacios dobles", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenNameStartsWithSpace() {
        String name = " Juan";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                NameValidator.validate(name));
        assertEquals("El nombre no puede comenzar ni terminar con espacio", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenNameEndsWithSpace() {
        String name = "Juan ";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                NameValidator.validate(name));
        assertEquals("El nombre no puede comenzar ni terminar con espacio", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenNameIsTooShort() {
        String name = "J";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                NameValidator.validate(name));
        assertEquals("El nombre debe tener al menos 2 caracteres", exception.getMessage());
    }

    @Test
    void validate_ShouldPass_WhenNameIsValid() {
        String name = "Juan Pérez";
        assertDoesNotThrow(() -> NameValidator.validate(name));
    }

    @Test
    void constructor_ShouldThrowException() throws Exception {
        var constructor = NameValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }
}
