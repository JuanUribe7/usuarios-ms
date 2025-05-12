package com.users.users_ms.domain.services;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.NotFoundException;
import com.users.users_ms.commons.exceptions.UnauthorizedAccessException;
import com.users.users_ms.domain.model.LoginResponse;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.model.UserDetailsDomain;
import com.users.users_ms.domain.ports.out.TokenProviderPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    private UserPersistencePort userPort;
    private TokenProviderPort tokenProvider;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        userPort = mock(UserPersistencePort.class);
        tokenProvider = mock(TokenProviderPort.class);
        authenticationService = new AuthenticationService(userPort, tokenProvider);
    }

    @Test
    void authenticateUser_ShouldReturnLoginResponse_WhenUserIsValid() {
        // Arrange
        String email = "test@example.com";
        UserDetailsDomain userDetails = new UserDetailsDomain(email, Set.of("ROLE_USER"));
        User user = new User(1L, "John", "Doe", "123456789", email, "password", "+123456789", LocalDate.of(1990, 1, 1), Role.CLIENT);
        String token = "mockedToken";

        when(userPort.findByEmail(email)).thenReturn(Optional.of(user));
        when(tokenProvider.generateToken(userDetails, user.getId(), user.getRole().name())).thenReturn(token);

        // Act
        LoginResponse response = authenticationService.authenticateUser(email, userDetails);

        // Assert
        assertNotNull(response);
        assertEquals(token, response.getToken());
        assertEquals(email, response.getEmail());
        assertEquals(user.getRole().name(), response.getRole());
        verify(userPort, times(1)).findByEmail(email);
        verify(tokenProvider, times(1)).generateToken(userDetails, user.getId(), user.getRole().name());
    }

    @Test
    void authenticateUser_ShouldThrowNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        String email = "nonexistent@example.com";
        UserDetailsDomain userDetails = new UserDetailsDomain(email, Set.of("ROLE_USER"));

        when(userPort.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> authenticationService.authenticateUser(email, userDetails));
        assertEquals(ExceptionMessages.USER_NOT_FOUND, exception.getMessage());
        verify(userPort, times(1)).findByEmail(email);
        verifyNoInteractions(tokenProvider);
    }

    @Test
    void validateRole_ShouldThrowUnauthorizedAccessException_WhenRoleIsNull() {
        // Act & Assert
        UnauthorizedAccessException exception = assertThrows(UnauthorizedAccessException.class, () -> authenticationService.validateRole(null));
        assertEquals(ExceptionMessages.ROLE_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    void validateRole_ShouldThrowUnauthorizedAccessException_WhenRoleIsEmpty() {
        // Act & Assert
        UnauthorizedAccessException exception = assertThrows(UnauthorizedAccessException.class, () -> authenticationService.validateRole(""));
        assertEquals(ExceptionMessages.ROLE_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    void validateRole_ShouldNotThrowException_WhenRoleIsValid() {
        // Act & Assert
        assertDoesNotThrow(() -> authenticationService.validateRole("ROLE_USER"));
    }

    @Test
    void validateAndGetUser_ShouldReturnUser_WhenUserExists() {
        // Arrange
        String email = "test@example.com";
        User user = new User(1L, "John", "Doe", "123456789", email, "password", "+123456789", LocalDate.of(1990, 1, 1), Role.OWNER);

        when(userPort.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        User result = authenticationService.validateAndGetUser(email);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);
        verify(userPort, times(1)).findByEmail(email);
    }

    @Test
    void validateAndGetUser_ShouldThrowNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        String email = "nonexistent@example.com";

        when(userPort.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> authenticationService.validateAndGetUser(email));
        assertEquals(ExceptionMessages.USER_NOT_FOUND, exception.getMessage());
        verify(userPort, times(1)).findByEmail(email);
    }
}