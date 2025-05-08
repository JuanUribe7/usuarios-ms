// src/test/java/com/users/users_ms/domain/validation/FieldValidationUtilsTest.java
package com.users.users_ms.domain.validation;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;
import com.users.users_ms.commons.exceptions.AlreadyExistsException;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FieldValidationUtilsTest {


    @Test
    void constructorThrowsWrapped() throws Exception {
        Constructor<FieldValidationUtils> ctor = FieldValidationUtils.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        InvocationTargetException ite = assertThrows(InvocationTargetException.class, ctor::newInstance);
        Throwable cause = ite.getCause();
        assertTrue(cause instanceof UnsupportedOperationException);
        assertEquals("Utility class cannot be instantiated", cause.getMessage());
    }


    @Test
    void validateNameValid() {
        assertDoesNotThrow(() -> FieldValidationUtils.validateName("Juan Pérez"));
    }

    @Test
    void validateDocumentInvalidStart() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
            () -> FieldValidationUtils.validateDocument("01234567"));
        assertEquals(ExceptionMessages.DOCUMENT_INVALID_START, ex.getMessage());
    }

    @Test
    void validateEmailUniqueness() {
        var port = Mockito.mock(UserPersistencePort.class);
        Mockito.when(port.existsByEmail("a@b.com")).thenReturn(true);
        AlreadyExistsException ex = assertThrows(AlreadyExistsException.class,
            () -> FieldValidationUtils.validateEmail("a@b.com", port));
        assertTrue(ex.getMessage().contains("email"));
    }

    @Test
    void validatePhoneValid() {
        assertDoesNotThrow(() -> FieldValidationUtils.validatePhone("+1234567890"));
    }

    @Test
    void validatePasswordValid() {
        assertDoesNotThrow(() -> FieldValidationUtils.validatePassword("Abcdef1!"));
    }

    @Test
    void validateBirthDateValid() {
        LocalDate adult = LocalDate.now().minusYears(20);
        assertDoesNotThrow(() -> FieldValidationUtils.validateBirthDate(adult));
    }
}
