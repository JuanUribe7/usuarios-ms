package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RegisterOwnerUseCaseTest {

    private UserPersistencePort userPort;
    private PasswordEncoderPort encoderPort;
    private RegisterOwnerUseCase useCase;

    @BeforeEach
    void setUp() {
        userPort = mock(UserPersistencePort.class);
        encoderPort = mock(PasswordEncoderPort.class);
        useCase = new RegisterOwnerUseCase(userPort, encoderPort);
    }

    @Test
    void execute_shouldCreateOwnerEncodePasswordAndSaveUser() {
        // Arrange
        User inputUser = mock(User.class);
        User ownerCreated = mock(User.class);
        User ownerWithEncodedPass = mock(User.class);

        when(inputUser.createOwner(userPort)).thenReturn(ownerCreated);
        when(ownerCreated.getPassword()).thenReturn("raw123");
        when(encoderPort.encodePassword("raw123")).thenReturn("encoded123");
        when(ownerCreated.withEncodedPassword("encoded123")).thenReturn(ownerWithEncodedPass);
        when(userPort.saveUser(ownerWithEncodedPass)).thenReturn(ownerWithEncodedPass);

        // Act
        User result = useCase.execute(inputUser);

        // Assert
        assertEquals(ownerWithEncodedPass, result);
        verify(inputUser).createOwner(userPort);
        verify(ownerCreated).getPassword();
        verify(encoderPort).encodePassword("raw123");
        verify(ownerCreated).withEncodedPassword("encoded123");
        verify(userPort).saveUser(ownerWithEncodedPass);
    }
}
