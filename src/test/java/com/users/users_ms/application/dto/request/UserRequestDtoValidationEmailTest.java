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

public class UserRequestDtoValidationEmailTest {

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
        dto.setPhone("+573005698325");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("juan@example.com");
        dto.setPassword("1042241877Ju@n");
        return dto;
    }

    @Test
    void whenEmailIsValid_thenValid() {
        UserRequestDto dto = baseDto();
        dto.setEmail("usuario.ejemplo@dominio.com");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        violations.forEach(v -> System.out.println(
                "Campo: " + v.getPropertyPath() + " - Error: " + v.getMessage())
        );

        assertTrue(violations.isEmpty());
    }

    @Test
    void whenEmailIsNull_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setEmail(null);
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("no puede estar vacío")));
    }

    @Test
    void whenEmailIsEmpty_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setEmail("");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("no puede estar vacío")));
    }

    @Test
    void whenEmailExceedsMaxLength_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setEmail("a".repeat(245) + "@dominio.com");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("no puede superar los 254 caracteres")));
    }

    @Test
    void whenEmailHasSpaces_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setEmail("usuario ejemplo@dominio.com");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("no puede contener espacios")));
    }

    @Test
    void whenEmailHasInvalidFormat_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setEmail("usuario@@dominio..com");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("Formato de correo electrónico no válido")));
    }

    @Test
    void whenEmailDomainStartsWithHyphen_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setEmail("usuario@-dominio.com");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("no puede comenzar ni terminar con '-'")));
    }

    @Test
    void whenEmailDomainEndsWithHyphen_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setEmail("usuario@dominio-.com");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("no puede comenzar ni terminar con '-'")));
    }

}
