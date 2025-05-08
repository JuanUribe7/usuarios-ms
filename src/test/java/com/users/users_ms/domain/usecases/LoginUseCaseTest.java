package com.users.users_ms.domain.usecases;

import com.users.users_ms.application.dto.response.LoginResponseDto;
import com.users.users_ms.domain.ports.out.AuthenticationPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginUseCaseTest {

    @Test
    void login_shouldReturnLoginResponseDto() {
        // Arrange
        String email = "juan@example.com";
        String password = "1042241877Ju@n";
        LoginResponseDto expectedResponse = new LoginResponseDto("mocked.jwt.token", email, "CLIENT");

        AuthenticationPort authenticationPort = Mockito.mock(AuthenticationPort.class);
        LoginUseCase loginUseCase = new LoginUseCase(authenticationPort);

        // Mocking the behavior of the AuthenticationPort
        when(authenticationPort.authenticate(email, password)).thenReturn(expectedResponse);

        // Act
        LoginResponseDto actualResponse = loginUseCase.login(email, password);

        // Assert
        assertEquals(expectedResponse.getToken(), actualResponse.getToken());
        assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());
        assertEquals(expectedResponse.getRole(), actualResponse.getRole());

        // Verifying that authenticate was called exactly once with the correct parameters
        verify(authenticationPort, times(1)).authenticate(email, password);
    }
}
