// src/test/java/com/users/users_ms/domain/validation/AgeValidatorTest.java

package com.users.users_ms.domain.validation;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AgeValidatorTest {

    @Test
    void validate_ShouldThrowException_WhenBirthDateIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                AgeValidator.validate(null));
        assertEquals("La fecha de nacimiento no puede estar vacía", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenBirthDateIsInFuture() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                AgeValidator.validate(futureDate));
        assertEquals("La fecha de nacimiento no puede estar en el futuro", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenBirthDateIsBefore1900() {
        LocalDate oldDate = LocalDate.of(1899, 12, 31);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                AgeValidator.validate(oldDate));
        assertEquals("La fecha de nacimiento no puede ser anterior a 1900", exception.getMessage());
    }

    @Test
    void validate_ShouldThrowException_WhenUserIsUnderage() {
        LocalDate underageDate = LocalDate.now().minusYears(17);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                AgeValidator.validate(underageDate));
        assertEquals("Debe ser mayor de edad (18+)", exception.getMessage());
    }

    @Test
    void validate_ShouldNotThrowException_WhenDateIsValid() {
        LocalDate validDate = LocalDate.now().minusYears(20);
        assertDoesNotThrow(() -> AgeValidator.validate(validDate));
    }

    @Test
    void constructor_ShouldThrowException() throws Exception {
        var constructor = AgeValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
        assertEquals("Clase utilitaria, no debe instanciarse.", exception.getCause().getMessage());
    }

}