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

class UserRequestDtoValidationNameTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private UserRequestDto baseDto() {
        UserRequestDto dto = new UserRequestDto();
        dto.setLastName("Perez");
        dto.setIdentityDocument("12345678");
        dto.setPhone("+573005698325");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("juan@example.com");
        dto.setPassword("1042241877Ju@n");
        return dto;
    }

    @Test
    void whenNameIsNull_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setName(null);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")
                        && v.getMessage().equals("El nombre no puede estar vacío")));
    }

    @Test
    void whenNameIsEmpty_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setName("  ");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")
                        && v.getMessage().equals("El nombre no puede estar vacío")));
    }

    @Test
    void whenNameHasNumbers_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setName("Juan123");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("solo puede contener letras")));
    }

    @Test
    void whenNameHasDoubleSpaces_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setName("Juan  Pedro");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("no puede tener espacios dobles")));
    }

    @Test
    void whenNameStartsWithSpace_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setName(" Juan");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("no puede comenzar ni terminar con espacio")));
    }

    @Test
    void whenNameEndsWithSpace_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setName("Juan ");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("no puede comenzar ni terminar con espacio")));
    }

    @Test
    void whenNameIsTooShort_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setName("J");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("tener al menos 2 caracteres")));
    }

    @Test
    void whenNameIsValid_thenNoViolations() {
        UserRequestDto dto = baseDto();
        dto.setName("Juan");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream()
                .noneMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

}