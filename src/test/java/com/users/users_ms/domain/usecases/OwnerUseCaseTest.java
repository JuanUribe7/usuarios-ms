package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.IUserPersistencePort;
import com.users.users_ms.domain.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OwnerUseCaseTest {

    private IUserPersistencePort userPersistencePort;
    private PasswordEncoder passwordEncoder;
    private OwnerUseCase ownerUseCase;

    @BeforeEach
    void setUp() {
        userPersistencePort = mock(IUserPersistencePort.class);
        passwordEncoder = mock(PasswordEncoder.class);
        ownerUseCase = new OwnerUseCase(userPersistencePort, passwordEncoder);
    }

    @Test
    void saveOwner_ShouldSave_WhenValid() {
        User owner = new User();
        owner.setPassword("plainPassword");

        try (MockedStatic<Validator> validatorMock = Mockito.mockStatic(Validator.class)) {
            when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
            when(userPersistencePort.saveUser(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

            User saved = ownerUseCase.saveOwner(owner);

            validatorMock.verify(() -> Validator.validate(owner, userPersistencePort));
            assertEquals(Role.OWNER, saved.getRole());
            assertEquals("encodedPassword", saved.getPassword());
            verify(userPersistencePort).saveUser(owner);
        }
    }

    @Test
    void saveOwner_ShouldThrow_WhenValidationFails() {
        User owner = new User();

        try (MockedStatic<Validator> validatorMock = Mockito.mockStatic(Validator.class)) {
            validatorMock.when(() -> Validator.validate(owner, userPersistencePort))
                    .thenThrow(new IllegalArgumentException("Validation failed"));

            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
                ownerUseCase.saveOwner(owner);
            });

            assertEquals("Validation failed", thrown.getMessage());
        }
    }


    @Test
    void findById_ShouldReturnUser_WhenExists() {
        User user = new User();
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = ownerUseCase.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void updateOwnerRestaurantId_ShouldUpdate_WhenValidOwner() {
        User owner = new User();
        owner.setRole(Role.OWNER);

        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(owner));

        ownerUseCase.updateOwnerRestaurantId(1L, 100L);

        assertEquals(100L, owner.getRestaurantId());
        verify(userPersistencePort).updateUser(owner);
    }

    @Test
    void updateOwnerRestaurantId_ShouldThrow_WhenOwnerNotFound() {
        when(userPersistencePort.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            ownerUseCase.updateOwnerRestaurantId(1L, 100L);
        });

        assertEquals("Propietario no encontrado o no válido.", thrown.getMessage());
    }

    @Test
    void updateOwnerRestaurantId_ShouldThrow_WhenNotOwnerRole() {
        User user = new User();
        user.setRole(Role.ADMIN); // No es OWNER

        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(user));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            ownerUseCase.updateOwnerRestaurantId(1L, 100L);
        });

        assertEquals("Propietario no encontrado o no válido.", thrown.getMessage());
    }
}