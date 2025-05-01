// src/test/java/com/users/users_ms/domain/validation/ValidatorTest.java

package com.users.users_ms.domain.validation;


import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ValidatorTest {
    private User user;
    private UserPersistencePort UserPersistencePort;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setIdentityDocument("12345678");
        user.setEmail("test@example.com");
        user.setName("Juan");
        user.setLastName("Perez");
        user.setPhone("+573001234567");
        user.setPassword("Password123!");

        UserPersistencePort = mock(UserPersistencePort.class);
        when(UserPersistencePort.findByIdentityDocument(anyString())).thenReturn(Optional.empty());
        when(UserPersistencePort.findByEmail(anyString())).thenReturn(Optional.empty());
    }

    @Test
    void validate_ShouldCallAllValidators() {
        try (MockedStatic<AgeValidator> ageMock = mockStatic(AgeValidator.class);
             MockedStatic<DocumentValidator> docMock = mockStatic(DocumentValidator.class);
             MockedStatic<EmailValidator> emailMock = mockStatic(EmailValidator.class);
             MockedStatic<NameValidator> nameMock = mockStatic(NameValidator.class);
             MockedStatic<PhoneValidator> phoneMock = mockStatic(PhoneValidator.class);
             MockedStatic<PassValidator> passMock = mockStatic(PassValidator.class)) {

            Validator.validate(user, UserPersistencePort);

            ageMock.verify(() -> AgeValidator.validate(user.getBirthDate()));
            docMock.verify(() -> DocumentValidator.validate(user.getIdentityDocument(), UserPersistencePort));
            emailMock.verify(() -> EmailValidator.validate(user.getEmail(), UserPersistencePort));
            nameMock.verify(() -> NameValidator.validate(user.getName()));
            nameMock.verify(() -> NameValidator.validate(user.getLastName()));
            phoneMock.verify(() -> PhoneValidator.validate(user.getPhone()));
            passMock.verify(() -> PassValidator.validate(user.getPassword()));
        }
    }

    @Test
    void constructor_ShouldThrowException() throws Exception {
        var constructor = Validator.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        Throwable cause = exception.getCause();
        assertTrue(cause instanceof UnsupportedOperationException);
        assertEquals("Clase utilitaria, no debe instanciarse.", cause.getMessage());
    }
}