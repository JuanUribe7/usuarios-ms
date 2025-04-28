// src/test/java/com/users/users_ms/domain/validation/EmailValidatorTest.java

package com.users.users_ms.domain.validation;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailValidatorTest {

    private IUserPersistencePort IUserPersistencePort;

    @BeforeEach
    void setUp() {
        IUserPersistencePort = mock(IUserPersistencePort.class);
    }

    @Test
    void validate_ShouldThrowException_WhenEmailAlreadyExists() {
        when(IUserPersistencePort.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate("test@example.com", IUserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenEmailIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate(null, IUserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenEmailIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate("   ", IUserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenEmailIsTooLong() {
        String longEmail = "a".repeat(245) + "@example.com";
        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate(longEmail, IUserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenEmailContainsSpaces() {
        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate("test @example.com", IUserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenEmailFormatIsInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate("invalid-email", IUserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenDomainStartsOrEndsWithHyphen() {
        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate("test@-example.com", IUserPersistencePort));
        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate("test@example-.com", IUserPersistencePort));
    }

    @Test
    void validate_ShouldPass_WhenEmailIsValidAndUnique() {
        when(IUserPersistencePort.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> EmailValidator.validate("test@example.com", IUserPersistencePort));
    }

    @Test
    void constructor_ShouldThrowException() throws Exception {
        var constructor = EmailValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }
}
