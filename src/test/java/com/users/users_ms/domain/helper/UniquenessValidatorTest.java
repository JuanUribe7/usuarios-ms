package com.users.users_ms.domain.helper;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.AlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniquenessValidatorTest {

    @Test
    void validate_ShouldThrowAlreadyExistsException_WhenExistenceCheckReturnsTrue() {
        // Arrange
        UniquenessValidator.ExistenceCheck existenceCheck = () -> true;
        String fieldName = "email";
        String value = "test@example.com";

        // Act & Assert
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class,
                () -> UniquenessValidator.validate(existenceCheck, fieldName, value));
        assertEquals(ExceptionMessages.VALUE_ALREADY_EXISTS + fieldName + ": " + value, exception.getMessage());
    }

    @Test
    void validate_ShouldNotThrowException_WhenExistenceCheckReturnsFalse() {
        // Arrange
        UniquenessValidator.ExistenceCheck existenceCheck = () -> false;
        String fieldName = "email";
        String value = "test@example.com";

        // Act & Assert
        assertDoesNotThrow(() -> UniquenessValidator.validate(existenceCheck, fieldName, value));
    }

    @Test
    void constructor_ShouldThrowUnsupportedOperationException_WhenInstantiated() {
        // Act & Assert
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                UniquenessValidator::new);
        assertEquals(ExceptionMessages.UTILITY_CLASS_INSTANTIATION, exception.getMessage());
    }
}