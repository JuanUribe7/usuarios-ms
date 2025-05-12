package com.users.users_ms.domain.services;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

class UserValidationServiceTest {

    private ValidationFieldsService validationFieldsService;
    private UserValidationService userValidationService;

    @BeforeEach
    void setUp() {
        validationFieldsService = mock(ValidationFieldsService.class);
        userValidationService = new UserValidationService(validationFieldsService);
    }

    @Test
    void validateUser_shouldCallAllValidationMethods() {
        // Arrange
        User user = new User(
                1L, // ID
                "John", // Name
                "Doe", // Last Name
                "123456789", // Identity Document
                "john.doe@example.com", // Email
                "password123", // Password
                "1234567890", // Phone
                LocalDate.parse("2000-01-01"), // Birth Date
                Role.CLIENT // Role
        );

        // Act
        userValidationService.validateUser(user);

        // Assert
        verify(validationFieldsService).validateName("John");
        verify(validationFieldsService).validateLastName("Doe");
        verify(validationFieldsService).validateIdentityDocument("123456789");
        verify(validationFieldsService).validateEmail("john.doe@example.com");
        verify(validationFieldsService).validatePassword("password123");
        verify(validationFieldsService).validatePhone("1234567890");
        verify(validationFieldsService).validateBirthDate(LocalDate.parse("2000-01-01"));
    }

    @Test
    void validateUser_shouldSkipLastNameValidationIfNullOrEmpty() {
        // Arrange
        User user = new User(
                2L, // ID
                "John", // Name
                "", // Last Name (empty)
                "123456789", // Identity Document
                "john.doe@example.com", // Email
                "password123", // Password
                "1234567890", // Phone
                LocalDate.parse("2000-01-01"), // Birth Date
                Role.OWNER // Role
        );

        // Act
        userValidationService.validateUser(user);

        // Assert
        verify(validationFieldsService).validateName("John");
        verify(validationFieldsService, never()).validateLastName(anyString());
        verify(validationFieldsService).validateIdentityDocument("123456789");
        verify(validationFieldsService).validateEmail("john.doe@example.com");
        verify(validationFieldsService).validatePassword("password123");
        verify(validationFieldsService).validatePhone("1234567890");
        verify(validationFieldsService).validateBirthDate(LocalDate.parse("2000-01-01"));
    }
}