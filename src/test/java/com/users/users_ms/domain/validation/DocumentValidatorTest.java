// src/test/java/com/users/users_ms/domain/validation/DocumentValidatorTest.java

package com.users.users_ms.domain.validation;

import com.users.users_ms.domain.exceptions.InvalidIdentityDocumentException;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentValidatorTest {

    private UserPersistencePort userPersistencePort;

    @BeforeEach
    void setUp() {
        userPersistencePort = mock(UserPersistencePort.class);
    }

    @Test
    void validate_ShouldThrowException_WhenDocumentAlreadyExists() {
        when(userPersistencePort.findByIdentityDocument("12345678")).thenReturn(Optional.of(new User()));

        assertThrows(InvalidIdentityDocumentException.class, () ->
                DocumentValidator.validate("12345678", userPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenDocumentIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                DocumentValidator.validate(null, userPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenDocumentIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                DocumentValidator.validate("   ", userPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenDocumentContainsNonNumeric() {
        assertThrows(IllegalArgumentException.class, () ->
                DocumentValidator.validate("abc123", userPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenDocumentLengthInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                DocumentValidator.validate("1234567", userPersistencePort)); // < 8
        assertThrows(IllegalArgumentException.class, () ->
                DocumentValidator.validate("123456789012", userPersistencePort)); // > 11
    }

    @Test
    void validate_ShouldThrowException_WhenDocumentStartsWithZero() {
        assertThrows(IllegalArgumentException.class, () ->
                DocumentValidator.validate("012345678", userPersistencePort));
    }

    @Test
    void validate_ShouldPass_WhenDocumentIsValidAndUnique() {
        when(userPersistencePort.findByIdentityDocument("12345678")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> DocumentValidator.validate("12345678", userPersistencePort));
    }

    @Test
    void constructor_ShouldThrowException() throws Exception {
        var constructor = DocumentValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }
}
