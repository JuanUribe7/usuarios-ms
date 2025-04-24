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

public class ValidatePhoneAndPassTest {
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
    void saveOwner_whenPhoneEmpty_shouldThrowException() {
        User user = baseValidUser();
        user.setPhone("");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenPhonePlusMisplaced_shouldThrowException() {
        User user = baseValidUser();
        user.setPhone("123+456");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenPhoneStartsWith00_shouldThrowException() {
        User user = baseValidUser();
        user.setPhone("+00123456789");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenPhoneInvalidFormat_shouldThrowException() {
        User user = baseValidUser();
        user.setPhone("+123");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenPasswordEmpty_shouldThrowException() {
        User user = baseValidUser();
        user.setPassword("");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenPasswordLengthInvalid_shouldThrowException() {
        User user = baseValidUser();
        user.setPassword("short");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenPasswordWhitespace_shouldThrowException() {
        User user = baseValidUser();
        user.setPassword(" Password1! ");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenPasswordNoUppercase_shouldThrowException() {
        User user = baseValidUser();
        user.setPassword("password1!");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenPasswordNoLowercase_shouldThrowException() {
        User user = baseValidUser();
        user.setPassword("PASSWORD1!");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenPasswordNoDigit_shouldThrowException() {
        User user = baseValidUser();
        user.setPassword("Password!");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenPasswordNoSpecialChar_shouldThrowException() {
        User user = baseValidUser();
        user.setPassword("Password1");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }
}
