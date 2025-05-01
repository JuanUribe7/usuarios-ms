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

    private UserPersistencePort UserPersistencePort;

    @BeforeEach
    void setUp() {
        UserPersistencePort = mock(UserPersistencePort.class);
    }

    @Test
    void validate_ShouldThrowException_WhenDocumentAlreadyExists() {
        when(UserPersistencePort.findByIdentityDocument("12345678")).thenReturn(Optional.of(new User()));

        assertThrows(InvalidIdentityDocumentException.class, () ->
                DocumentValidator.validate("12345678", UserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenDocumentIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                DocumentValidator.validate(null, UserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenDocumentIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                DocumentValidator.validate("   ", UserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenDocumentContainsNonNumeric() {
        assertThrows(IllegalArgumentException.class, () ->
                DocumentValidator.validate("abc123", UserPersistencePort));
    }

    @Test
    void validate_ShouldThrowException_WhenDocumentLengthInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                DocumentValidator.validate("1234567", UserPersistencePort)); // < 8
        assertThrows(IllegalArgumentException.class, () ->
                DocumentValidator.validate("123456789012", UserPersistencePort)); // > 11
    }

    @Test
    void validate_ShouldThrowException_WhenDocumentStartsWithZero() {
        assertThrows(IllegalArgumentException.class, () ->
                DocumentValidator.validate("012345678", UserPersistencePort));
    }

    @Test
    void validate_ShouldPass_WhenDocumentIsValidAndUnique() {
        when(UserPersistencePort.findByIdentityDocument("12345678")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> DocumentValidator.validate("12345678", UserPersistencePort));
    }

    @Test
    void constructor_ShouldThrowException() throws Exception {
        var constructor = DocumentValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }
}
