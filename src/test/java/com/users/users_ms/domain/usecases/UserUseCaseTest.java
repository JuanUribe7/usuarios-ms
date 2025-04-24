package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.ports.out.UserPersistencePort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

public class UserUseCaseTest {
    private UserUseCase userUseCase;
    private UserPersistencePort userPersistencePort;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userPersistencePort = mock(UserPersistencePort.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userUseCase = new UserUseCase(userPersistencePort, passwordEncoder);
    }

    @Test
    void saveOwner_whenAllValid_shouldSaveSuccessfully() {
        User user = new User();
        user.setBirthDate(LocalDate.of(1990, 1, 1));      // válido
        user.setIdentityDocument("12345678");             // válido
        user.setEmail("valid@example.com");               // válido
        user.setName("Nombre");                           // válido
        user.setLastName("Apellido");                     // válido
        user.setPhone("+1234567890");                     // válido
        user.setPassword("Password1!");                   // válido

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setRole(Role.OWNER);

        when(passwordEncoder.encode("Password1!")).thenReturn("EncodedPassword");
        when(userPersistencePort.saveUser(any(User.class))).thenReturn(savedUser);

        User result = userUseCase.saveOwner(user);

        assertNotNull(result);
        assertEquals(Role.OWNER, result.getRole());
        verify(passwordEncoder).encode("Password1!");
        verify(userPersistencePort).saveUser(user);
    }
}

