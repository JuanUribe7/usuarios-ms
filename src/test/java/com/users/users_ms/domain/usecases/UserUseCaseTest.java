// src/test/java/com/users/users_ms/domain/usecases/UserUseCaseTest.java

package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    private UserPersistencePort UserPersistencePort;
    private PasswordEncoder passwordEncoder;
    private RegisterOwnerUseCase userUseCase;

    @BeforeEach
    void setUp() {
        UserPersistencePort = mock(UserPersistencePort.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userUseCase = new RegisterOwnerUseCase(UserPersistencePort, passwordEncoder);
    }


    @Test
    void saveOwner_ShouldSetRoleEncodePasswordAndSaveUser() {
        User user = new User();
        user.setPassword("1042241877Ju@n"); // Usado valor real aquí
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setName("Juan");
        user.setEmail("test@example.com");
        user.setIdentityDocument("12345678");
        user.setPhone("+573001234567");
        user.setLastName("Perez");

        when(passwordEncoder.encode("1042241877Ju@n")).thenReturn("encodedPassword"); // Coincide ahora
        when(UserPersistencePort.saveUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userUseCase.saveOwner(user);

        assertEquals(Role.OWNER, savedUser.getRole());
        assertEquals("encodedPassword", savedUser.getPassword());
        verify(UserPersistencePort).saveUser(savedUser);
    }




    @Test
    void findById_ShouldReturnUserIfExists() {
        User user = new User();
        user.setId(1L);
        when(UserPersistencePort.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userUseCase.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }
}
