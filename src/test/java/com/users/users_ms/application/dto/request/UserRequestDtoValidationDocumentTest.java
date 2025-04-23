package com.users.users_ms.application.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRequestDtoValidationDocumentTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

     private UserRequestDto baseDto() {
        UserRequestDto dto = new UserRequestDto();
        dto.setName("Juan");
        dto.setLastName("Perez");
        dto.setPhone("+573005698325");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("juan@example.com");
        dto.setPassword("1042241877Ju");
        return dto;
    }

    @Test
    void whenIdentityDocumentIsNull_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setIdentityDocument(null);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El documento de identidad no puede estar vacío")));
    }

    @Test
    void whenIdentityDocumentIsEmpty_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setIdentityDocument("  ");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El documento de identidad no puede estar vacío")));
    }

    @Test
    void whenIdentityDocumentHasLetters_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setIdentityDocument("ABC12345");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El documento de identidad debe contener solo números")));
    }

    @Test
    void whenIdentityDocumentTooShort_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setIdentityDocument("1234567");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El documento de identidad debe tener entre 8 y 11 dígitos")));
    }

    @Test
    void whenIdentityDocumentTooLong_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setIdentityDocument("123456789012");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El documento de identidad debe tener entre 8 y 11 dígitos")));
    }

    @Test
    void whenIdentityDocumentStartsWithZero_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setIdentityDocument("012345678");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El documento de identidad no puede comenzar con 0")));
    }

    @Test
    void whenIdentityDocumentIsValid_thenNoViolations() {
        UserRequestDto dto = baseDto();
        dto.setIdentityDocument("12345678");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .noneMatch(v -> v.getPropertyPath().toString().equals("identityDocument")));
    }
}
