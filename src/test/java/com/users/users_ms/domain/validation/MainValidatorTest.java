package com.users.users_ms.domain.validation;

import com.users.users_ms.commons.exceptions.InvalidFieldException;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainValidatorTest {

    @Test
    void validate_validUser_shouldPass() {
        UserPersistencePort port = mock(UserPersistencePort.class);
        when(port.existsByEmail("email@test.com")).thenReturn(false);

        User user = new User(1L, "Juan", "Pérez", "12345678", "email@test.com",
                "Valid123!", "3001234567", LocalDate.now().minusYears(20), null);

        assertDoesNotThrow(() -> MainValidator.validate(user, port));
    }

    @Test
    void validate_userWithInvalidFields_shouldThrow() {
        UserPersistencePort port = mock(UserPersistencePort.class);
        User user = new User(1L, "", "", "abc", "bademail", "weak", "abc", LocalDate.now(), null);
        when(port.existsByEmail("email@test.com")).thenReturn(false);

        assertThrows(InvalidFieldException.class, () -> MainValidator.validate(user, port));
    }
}