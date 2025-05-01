// src/test/java/com/users/users_ms/domain/validation/EmailValidatorTest.java

package com.users.users_ms.domain.validation;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailValidatorTest {

    private UserPersistencePort UserPersistencePort;

    @BeforeEach
    void setUp() {
        UserPersistencePort = mock(UserPersistencePort.class);
    }

    @Test
    void validate_ShouldThrowException_WhenEmailAlreadyExists() {
        when(UserPersistencePort.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate("test@example.com", UserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenEmailIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate(null, UserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenEmailIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate("   ", UserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenEmailIsTooLong() {
        String longEmail = "a".repeat(245) + "@example.com";
        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate(longEmail, UserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenEmailContainsSpaces() {
        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate("test @example.com", UserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenEmailFormatIsInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate("invalid-email", UserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenDomainStartsOrEndsWithHyphen() {
        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate("test@-example.com", UserPersistencePort));
        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate("test@example-.com", UserPersistencePort));
    }

    @Test
    void validate_ShouldPass_WhenEmailIsValidAndUnique() {
        when(UserPersistencePort.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> EmailValidator.validate("test@example.com", UserPersistencePort));
    }

    @Test
    void constructor_ShouldThrowException() throws Exception {
        var constructor = EmailValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }
}
