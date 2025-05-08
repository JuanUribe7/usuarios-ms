// src/test/java/com/users/users_ms/domain/validation/core/DateValidatorTest.java
package com.users.users_ms.domain.validation.core;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateValidatorTest {

    @Test
    void constructorThrowsWrapped() throws Exception {
        Constructor<DateValidator> ctor = DateValidator.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        InvocationTargetException ite = assertThrows(InvocationTargetException.class, ctor::newInstance);
        Throwable cause = ite.getCause();
        assertTrue(cause instanceof UnsupportedOperationException);
        assertEquals("Utility class cannot be instantiated", cause.getMessage());
    }

    @Test
    void validAdultBirthDate() {
        LocalDate adult = LocalDate.now().minusYears(20);
        assertDoesNotThrow(() -> DateValidator.validateAdultBirthDate(adult));
    }

    @Test
    void futureBirthDate() {
        LocalDate future = LocalDate.now().plusDays(1);
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
                () -> DateValidator.validateAdultBirthDate(future));
        assertEquals(ExceptionMessages.BIRTHDATE_FUTURE, ex.getMessage());
    }

    @Test
    void tooOldBirthDate() {
        LocalDate old = LocalDate.of(1800,1,1);
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
                () -> DateValidator.validateAdultBirthDate(old));
        assertEquals(ExceptionMessages.BIRTHDATE_TOO_OLD, ex.getMessage());
    }

    @Test
    void underageBirthDate() {
        LocalDate underage = LocalDate.now().minusYears(10);
        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
                () -> DateValidator.validateAdultBirthDate(underage));
        assertEquals(ExceptionMessages.BIRTHDATE_NOT_ADULT, ex.getMessage());
    }
}
