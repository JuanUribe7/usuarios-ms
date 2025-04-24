package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

public class ValidationAgeAndDocumentTest {
    private UserPersistencePort userPersistencePort;
    private PasswordEncoder passwordEncoder;
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        userPersistencePort = mock(UserPersistencePort.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userUseCase = new UserUseCase(userPersistencePort, passwordEncoder);
    }

    private User baseValidUser() {
        User user = new User();
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setIdentityDocument("12345678");
        user.setEmail("valid@example.com");
        user.setName("Nombre");
        user.setLastName("Apellido");
        user.setPhone("+1234567890");
        user.setPassword("Password1!");
        return user;
    }

    @Test
    void saveOwner_whenAgeNull_shouldThrowException() {
        User user = baseValidUser();
        user.setBirthDate(null);
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenBirthDateFuture_shouldThrowException() {
        User user = baseValidUser();
        user.setBirthDate(LocalDate.now().plusDays(1));
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenBirthDateBefore1900_shouldThrowException() {
        User user = baseValidUser();
        user.setBirthDate(LocalDate.of(1800, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenUnderage_shouldThrowException() {
        User user = baseValidUser();
        user.setBirthDate(LocalDate.now().minusYears(15));
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenDocumentEmpty_shouldThrowException() {
        User user = baseValidUser();
        user.setIdentityDocument("");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenDocumentNonNumeric_shouldThrowException() {
        User user = baseValidUser();
        user.setIdentityDocument("abc123");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenDocumentLengthInvalid_shouldThrowException() {
        User user = baseValidUser();
        user.setIdentityDocument("123");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenDocumentStartsWithZero_shouldThrowException() {
        User user = baseValidUser();
        user.setIdentityDocument("0123456789");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }
}
