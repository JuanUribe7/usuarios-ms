package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.LoginResponse;
import com.users.users_ms.domain.ports.out.AuthenticationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LoginUseCaseTest {

    private AuthenticationPort authenticationPort;
    private LoginUseCase loginUseCase;

    @BeforeEach
    void setUp() {
        authenticationPort = mock(AuthenticationPort.class);
        loginUseCase = new LoginUseCase(authenticationPort);
    }

    @Test
    void login_shouldReturnLoginResponseWhenAuthenticationSucceeds() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        LoginResponse expectedResponse = new LoginResponse("token123", "userId123@gmail.com","NAME");

        when(authenticationPort.authenticate(email, password)).thenReturn(expectedResponse);

        // Act
        LoginResponse actualResponse = loginUseCase.login(email, password);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(authenticationPort).authenticate(email, password);
    }

    @Test
    void login_shouldThrowExceptionWhenEmailOrPasswordIsNull() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> loginUseCase.login(null, "password123"));
        assertThrows(NullPointerException.class, () -> loginUseCase.login("test@example.com", null));
    }

    @Test
    void login_shouldThrowExceptionWhenAuthenticationFails() {
        // Arrange
        String email = "test@example.com";
        String password = "wrongPassword";

        when(authenticationPort.authenticate(email, password)).thenThrow(new RuntimeException("Authentication failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> loginUseCase.login(email, password));
        assertEquals("Authentication failed", exception.getMessage());
        verify(authenticationPort).authenticate(email, password);
    }
}