package com.users.users_ms.application.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;


class UserRequestDtoValidationPhoneTest {

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
        dto.setIdentityDocument("12345678");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("juan@example.com");
        dto.setPassword("1042241877Ju");
        return dto;
    }

    @Test
    void whenPhoneIsNull_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPhone(null);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("phone")
                        && v.getMessage().equals("El número de teléfono no puede estar vacío")));
    }

    @Test
    void whenPhoneIsEmpty_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPhone("   ");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("phone")
                        && v.getMessage().equals("El número de teléfono no puede estar vacío")));
    }

    @Test
    void whenPhoneContainsLetters_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPhone("+57300ABC325");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("entre 10 y 13 caracteres")));
    }

    @Test
    void whenPhoneHasMultiplePlusSigns_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPhone("++573005698325");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("El '+' solo puede estar al inicio")));
    }

    @Test
    void whenPhoneStartsWith00_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPhone("00573005698325");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("no puede comenzar con '00'")));
    }

    @Test
    void whenPhoneTooShort_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPhone("57300");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("entre 10 y 13 caracteres")));
    }

    @Test
    void whenPhoneTooLong_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPhone("+57300569832599");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("entre 10 y 13 caracteres")));
    }

    @Test
    void whenPhoneValidWithoutPlus_thenNoViolations() {
        UserRequestDto dto = baseDto();
        dto.setPhone("3005698325");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .noneMatch(v -> v.getPropertyPath().toString().equals("phone")));
    }

    @Test
    void whenPhoneValidWithPlus_thenNoViolations() {
        UserRequestDto dto = baseDto();
        dto.setPhone("+573005698325");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}