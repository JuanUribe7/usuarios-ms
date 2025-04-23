package com.users.users_ms.application.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRequestDtoValidationBirthDateTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private UserRequestDto baseDto() {
        UserRequestDto dto = new UserRequestDto();
        dto.setName("Juan");
        dto.setLastName("Perez");
        dto.setIdentityDocument("1042241877");
        dto.setPhone("+573005698325");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("juan@example.com");
        dto.setPassword("1042241877@Juan");
        return dto;
    }

    @Test
    void whenBirthDateIsExactly18YearsAgo_thenValid() {
        UserRequestDto dto = baseDto();
        dto.setBirthDate(LocalDate.now().minusYears(18));
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        violations.forEach(v -> System.out.println(v.getMessage()));
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenBirthDateIsNull_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setBirthDate(null);
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("obligatoria")));
    }

    @Test
    void whenBirthDateIsInFuture_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setBirthDate(LocalDate.now().plusDays(1));
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("futuro")));
    }

    @Test
    void whenBirthDateIsBefore1900_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setBirthDate(LocalDate.of(1899, 12, 31));
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("demasiado antigua")));
    }

    @Test
    void whenBirthDateIsUnder18_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setBirthDate(LocalDate.now().minusYears(17));
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("mayor de edad")));
    }
}
