package com.users.users_ms.domain.validation;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.AlreadyExistsException;
import com.users.users_ms.commons.exceptions.InvalidFieldException;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldValidationUtilsTest {

    private final UserPersistencePort port = mock(UserPersistencePort.class);

    @Test
    void validateName_valid() {
        assertDoesNotThrow(() -> FieldValidationUtils.validateName("Juan Pérez"));
    }

    @Test
    void validateDocument_valid() {
        assertDoesNotThrow(() -> FieldValidationUtils.validateDocument("123456789"));
    }

    @Test
    void validateEmail_valid() {
        when(port.existsByEmail("email@test.com")).thenReturn(false);
        assertDoesNotThrow(() -> FieldValidationUtils.validateEmail("email@test.com", port));
    }

    @Test
    void validateEmail_alreadyExists() {
        when(port.existsByEmail("email@test.com")).thenReturn(true);
        Exception ex = assertThrows(AlreadyExistsException.class,
                () -> FieldValidationUtils.validateEmail("email@test.com", port));
        assertEquals("Value already exists for email: email@test.com", ex.getMessage());
    }

    @Test
    void validatePhone_valid() {
        assertDoesNotThrow(() -> FieldValidationUtils.validatePhone("+573001234567"));
    }

    @Test
    void validatePhone_invalidPlusPosition() {
        Exception ex = assertThrows(InvalidFieldException.class,
                () -> FieldValidationUtils.validatePhone("300+1234567"));
        assertEquals(ExceptionMessages.PHONE_PLUS_ERROR, ex.getMessage());
    }

    @Test
    void validatePassword_valid() {
        assertDoesNotThrow(() -> FieldValidationUtils.validatePassword("Valid123!"));
    }

    @Test
    void validateBirthDate_valid() {
        assertDoesNotThrow(() -> FieldValidationUtils.validateBirthDate(LocalDate.now().minusYears(20)));
    }
}