// src/test/java/com/users/users_ms/domain/validation/MainValidatorTest.java
package com.users.users_ms.domain.validation;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.AlreadyExistsException;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MainValidatorTest {

    // MainValidatorTest.java
    @Test
    void constructorThrowsWrapped() throws Exception {
        Constructor<MainValidator> ctor = MainValidator.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        InvocationTargetException ite = assertThrows(InvocationTargetException.class, ctor::newInstance);
        Throwable cause = ite.getCause();
        assertTrue(cause instanceof UnsupportedOperationException);
        assertEquals("Utility class cannot be instantiated", cause.getMessage());
    }


    private User mockUser(String name, String lastName, String doc, String email,
                          String phone, String pwd, LocalDate dob) {
        User u = Mockito.mock(User.class);
        Mockito.when(u.getName()).thenReturn(name);
        Mockito.when(u.getLastName()).thenReturn(lastName);
        Mockito.when(u.getIdentityDocument()).thenReturn(doc);
        Mockito.when(u.getEmail()).thenReturn(email);
        Mockito.when(u.getPhone()).thenReturn(phone);
        Mockito.when(u.getPassword()).thenReturn(pwd);
        Mockito.when(u.getBirthDate()).thenReturn(dob);
        return u;
    }

    @Test
    void validateAllFieldsValidWithLastName() {
        var user = mockUser("Juan","Perez","12345678","a@b.com","+1234567890","Abcdef1!",LocalDate.now().minusYears(30));
        var port = Mockito.mock(UserPersistencePort.class);
        Mockito.when(port.existsByEmail("a@b.com")).thenReturn(false);
        assertDoesNotThrow(() -> MainValidator.validate(user, port));
    }

    @Test
    void skipLastNameValidationWhenEmpty() {
        var user = mockUser("Juan","", "12345678","a@b.com","+1234567890","Abcdef1!",LocalDate.now().minusYears(30));
        var port = Mockito.mock(UserPersistencePort.class);
        Mockito.when(port.existsByEmail("a@b.com")).thenReturn(false);
        assertDoesNotThrow(() -> MainValidator.validate(user, port));
    }

    @Test
    void emailAlreadyExistsFails() {
        var user = mockUser("Juan","Perez","12345678","a@b.com","+1234567890","Abcdef1!",LocalDate.now().minusYears(30));
        var port = Mockito.mock(UserPersistencePort.class);
        Mockito.when(port.existsByEmail("a@b.com")).thenReturn(true);
        AlreadyExistsException ex = assertThrows(AlreadyExistsException.class,
            () -> MainValidator.validate(user, port));
        assertTrue(ex.getMessage().contains("email"));
    }
}
