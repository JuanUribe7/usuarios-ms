package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RegisterClientUseCaseTest {

    private UserPersistencePort userPort;
    private PasswordEncoderPort encoderPort;
    private RegisterClientUseCase useCase;

    @BeforeEach
    void setUp() {
        userPort = mock(UserPersistencePort.class);
        encoderPort = mock(PasswordEncoderPort.class);
        useCase = new RegisterClientUseCase(userPort, encoderPort);
    }

    @Test
    void saveClient_shouldCreateClientEncodePasswordAndSaveUser() {
        // Arrange
        User inputUser = mock(User.class);
        User clientCreated = mock(User.class);
        User clientWithEncodedPass = mock(User.class);

        when(inputUser.createClient(userPort)).thenReturn(clientCreated);
        when(clientCreated.getPassword()).thenReturn("12345");
        when(encoderPort.encodePassword("12345")).thenReturn("encoded12345");
        when(clientCreated.withEncodedPassword("encoded12345")).thenReturn(clientWithEncodedPass);
        when(userPort.saveUser(clientWithEncodedPass)).thenReturn(clientWithEncodedPass);

        // Act
        User result = useCase.saveClient(inputUser);

        // Assert
        assertEquals(clientWithEncodedPass, result);
        verify(inputUser).createClient(userPort);
        verify(clientCreated).getPassword();
        verify(encoderPort).encodePassword("12345");
        verify(clientCreated).withEncodedPassword("encoded12345");
        verify(userPort).saveUser(clientWithEncodedPass);
    }
}
