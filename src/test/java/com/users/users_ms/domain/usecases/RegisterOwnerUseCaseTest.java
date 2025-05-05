package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RegisterOwnerUseCaseTest {

    private UserPersistencePort userPersistencePort;
    private PasswordEncoderPort passwordEncoderPort;
    private RegisterOwnerUseCase useCase;

    @BeforeEach
    void setUp() {
        userPersistencePort = mock(UserPersistencePort.class);
        passwordEncoderPort = mock(PasswordEncoderPort.class);
        useCase = new RegisterOwnerUseCase(userPersistencePort, passwordEncoderPort);
    }

    @Test
    void execute_shouldCreateOwnerEncodePasswordAndSave() {
        // Arrange
        User inputUser = mock(User.class);
        User ownerCreated = mock(User.class);
        User finalUser = mock(User.class);

        when(inputUser.createOwner(userPersistencePort)).thenReturn(ownerCreated);
        when(ownerCreated.getPassword()).thenReturn("rawpass");
        when(passwordEncoderPort.encodePassword("rawpass")).thenReturn("encodedpass");
        when(ownerCreated.withEncodedPassword("encodedpass")).thenReturn(finalUser);
        when(userPersistencePort.saveUser(finalUser)).thenReturn(finalUser);

        // Act
        User result = useCase.execute(inputUser);

        // Assert
        assertEquals(finalUser, result);
        verify(inputUser).createOwner(userPersistencePort);
        verify(ownerCreated).getPassword();
        verify(passwordEncoderPort).encodePassword("rawpass");
        verify(ownerCreated).withEncodedPassword("encodedpass");
        verify(userPersistencePort).saveUser(finalUser);
    }
}
