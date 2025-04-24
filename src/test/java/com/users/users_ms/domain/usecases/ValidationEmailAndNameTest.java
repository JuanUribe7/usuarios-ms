package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.validation.EmailValidator;
import com.users.users_ms.domain.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class ValidationEmailAndNameTest {
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
    void saveOwner_whenEmailEmpty_shouldThrowException() {
        User user = baseValidUser();
        user.setEmail("");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenEmailTooLong_shouldThrowException() {
        User user = baseValidUser();
        user.setEmail("a".repeat(255) + "@example.com");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenEmailWithSpaces_shouldThrowException() {
        User user = baseValidUser();
        user.setEmail("email con espacios@example.com");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenEmailInvalidFormat_shouldThrowException() {
        User user = baseValidUser();
        user.setEmail("invalid-email");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenEmailDomainHyphen_shouldThrowException() {
        User user = baseValidUser();
        user.setEmail("test@-domain.com");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenNameInvalidChars_shouldThrowException() {
        User user = baseValidUser();
        user.setName("Nombre##");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenNameDoubleSpaces_shouldThrowException() {
        User user = baseValidUser();
        user.setName("Nombre  Doble");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenNameStartsOrEndsWithSpace_shouldThrowException() {
        User user = baseValidUser();
        user.setName(" Nombre");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }

    @Test
    void saveOwner_whenNameTooShort_shouldThrowException() {
        User user = baseValidUser();
        user.setName("A");
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveOwner(user));
    }
    @Test
    void validate_whenEmailAlreadyExists_shouldThrowException() {
        UserPersistencePort userPersistencePort = mock(UserPersistencePort.class);
        User mockUser = mock(User.class);
        when(userPersistencePort.findByEmail("exists@example.com")).thenReturn(Optional.of(mockUser));

        assertThrows(IllegalArgumentException.class, () ->
                EmailValidator.validate("exists@example.com", userPersistencePort));
    }


}
