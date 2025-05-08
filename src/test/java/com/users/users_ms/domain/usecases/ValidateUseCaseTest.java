package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.model.Role;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ValidateUseCaseTest {

    @Test
    void existsAndIsOwner_shouldReturnTrue_whenUserExistsAndIsOwner() {
        // Arrange
        Long userId = 1L;
        User user = new User(userId, "Juan", "Uribe", "1042241877@n", "juan@example.com", "1042241877Ju@n", "3103479455", LocalDate.now(), Role.OWNER);
        UserPersistencePort userPort = Mockito.mock(UserPersistencePort.class);
        ValidateUseCase validateUseCase = new ValidateUseCase(userPort);

        // Mocking the behavior of findById
        when(userPort.findById(userId)).thenReturn(Optional.of(user));

        // Act
        boolean result = validateUseCase.existsAndIsOwner(userId);

        // Assert
        assertTrue(result);
        verify(userPort, times(1)).findById(userId);
    }

    @Test
    void existsAndIsOwner_shouldReturnFalse_whenUserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        UserPersistencePort userPort = Mockito.mock(UserPersistencePort.class);
        ValidateUseCase validateUseCase = new ValidateUseCase(userPort);

        // Mocking the behavior of findById to return empty
        when(userPort.findById(userId)).thenReturn(Optional.empty());

        // Act
        boolean result = validateUseCase.existsAndIsOwner(userId);

        // Assert
        assertFalse(result);
        verify(userPort, times(1)).findById(userId);
    }

    @Test
    void existsAndIsOwner_shouldReturnFalse_whenUserExistsButIsNotOwner() {
        // Arrange
        Long userId = 1L;
        User user = new User(userId, "Juan", "Uribe", "1042241877Ju@n", "juan@example.com", "password", "123456789",LocalDate.now(), Role.CLIENT);
        UserPersistencePort userPort = Mockito.mock(UserPersistencePort.class);
        ValidateUseCase validateUseCase = new ValidateUseCase(userPort);

        // Mocking the behavior of findById
        when(userPort.findById(userId)).thenReturn(Optional.of(user));

        // Act
        boolean result = validateUseCase.existsAndIsOwner(userId);

        // Assert
        assertFalse(result);
        verify(userPort, times(1)).findById(userId);
    }
}
