package com.users.users_ms.domain.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;
import org.springframework.security.crypto.password.PasswordEncoder;

class RegisterClientUseCaseTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private PasswordEncoder passwordEncoder;

    private RegisterClientUseCase registerClientUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize @Mock fields
        registerClientUseCase = new RegisterClientUseCase(userPersistencePort, passwordEncoder); // Create the use case to test
    }

    @Test
    void shouldSaveClientSuccessfully() {
        // Arrange
        User client = new User();                           // Create a new User instance
        client.setPassword("plainPassword");                // Set an initial plain password

        // Mock static Validator.validate so it does nothing
        try (MockedStatic<Validator> validatorMock = mockStatic(Validator.class)) {
            when(passwordEncoder.encode("plainPassword"))
                    .thenReturn("encodedPassword");             // Stub encoding to return "encodedPassword"
            when(userPersistencePort.saveUser(any(User.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0)); // Return the passed-in user

            // Act
            User savedClient = registerClientUseCase.saveClient(client);

            // Assert
            validatorMock.verify(() -> Validator.validate(client, userPersistencePort)); // Validation was called
            verify(passwordEncoder).encode("plainPassword");           // PasswordEncoder.encode was called
            verify(userPersistencePort).saveUser(client);              // saveUser was called with our client
            assertEquals(Role.CLIENT, savedClient.getRole());          // Role should be CLIENT
            assertEquals("encodedPassword", savedClient.getPassword()); // Password should be updated
        }
    }

    @Test
    void shouldThrowWhenValidationFails() {
        // Arrange
        User client = new User(); // Create a new User

        // Mock static Validator.validate to throw an exception
        try (MockedStatic<Validator> validatorMock = mockStatic(Validator.class)) {
            validatorMock.when(() -> Validator.validate(client, userPersistencePort))
                    .thenThrow(new IllegalArgumentException("validation failed"));

            // Act & Assert
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> registerClientUseCase.saveClient(client)
            );
            assertEquals("validation failed", ex.getMessage()); // Exception message should match
        }
    }
}
