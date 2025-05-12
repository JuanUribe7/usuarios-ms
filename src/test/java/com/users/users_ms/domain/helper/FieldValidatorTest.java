package com.users.users_ms.domain.helper;


import com.users.users_ms.commons.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FieldValidatorTest {

    @Test
    void constructor_shouldThrowException_whenInstantiated() throws Exception {
        var constructor = FieldValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertThrows(UnsupportedOperationException.class, () -> {
            throw exception.getCause();
        });
    }
    @Test
    void validateNotBlank_shouldThrowException_whenFieldIsNull() {
        assertThrows(InvalidFieldException.class, () ->
                FieldValidator.validateNotBlank(null, "Field cannot be blank")
        );
    }

    @Test
    void validateNotBlank_shouldThrowException_whenFieldIsBlank() {
        assertThrows(InvalidFieldException.class, () ->
                FieldValidator.validateNotBlank("   ", "Field cannot be blank")
        );
    }

    @Test
    void validateNotBlank_shouldNotThrowException_whenFieldIsValid() {
        assertDoesNotThrow(() ->
                FieldValidator.validateNotBlank("ValidField", "Field cannot be blank")
        );
    }

    @Test
    void validatePattern_shouldThrowException_whenFieldDoesNotMatchPattern() {
        assertThrows(InvalidFieldException.class, () ->
                FieldValidator.validatePattern("123", "^[a-zA-Z]+$", "Field must contain only letters")
        );
    }

    @Test
    void validatePattern_shouldNotThrowException_whenFieldMatchesPattern() {
        assertDoesNotThrow(() ->
                FieldValidator.validatePattern("Valid", "^[a-zA-Z]+$", "Field must contain only letters")
        );
    }

    @Test
    void validateMaxLength_shouldThrowException_whenFieldExceedsMaxLength() {
        assertThrows(InvalidFieldException.class, () ->
                FieldValidator.validateMaxLength("TooLongField", 5, "Field exceeds max length")
        );
    }

    @Test
    void validateMaxLength_shouldNotThrowException_whenFieldIsWithinMaxLength() {
        assertDoesNotThrow(() ->
                FieldValidator.validateMaxLength("Valid", 10, "Field exceeds max length")
        );
    }

    @Test
    void validateMinLength_shouldThrowException_whenFieldIsShorterThanMinLength() {
        assertThrows(InvalidFieldException.class, () ->
                FieldValidator.validateMinLength("Short", 10, "Field is shorter than min length")
        );
    }

    @Test
    void validateMinLength_shouldNotThrowException_whenFieldMeetsMinLength() {
        assertDoesNotThrow(() ->
                FieldValidator.validateMinLength("ValidField", 5, "Field is shorter than min length")
        );
    }
}