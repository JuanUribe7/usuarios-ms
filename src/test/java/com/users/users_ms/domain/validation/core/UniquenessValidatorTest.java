// src/test/java/com/users/users_ms/domain/validation/core/UniquenessValidatorTest.java
package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.exceptions.AlreadyExistsException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class UniquenessValidatorTest {


    @Test
    void constructorThrowsWrapped() throws Exception {
        Constructor<UniquenessValidator> ctor = UniquenessValidator.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        InvocationTargetException ite = assertThrows(InvocationTargetException.class, ctor::newInstance);
        Throwable cause = ite.getCause();
        assertTrue(cause instanceof UnsupportedOperationException);
        assertEquals("Utility class cannot be instantiated", cause.getMessage());
    }


    @Test
    void validateNoExistence() {
        assertDoesNotThrow(() ->
            UniquenessValidator.validate(() -> false, "field", "val"));
    }

    @Test
    void validateAlreadyExists() {
        AlreadyExistsException ex = assertThrows(AlreadyExistsException.class,
            () -> UniquenessValidator.validate(() -> true, "email", "a@b"));
        assertTrue(ex.getMessage().contains("email"));
        assertTrue(ex.getMessage().contains("a@b"));
    }
}
