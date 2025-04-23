package com.users.users_ms.application.dto.request;

import com.users.users_ms.commons.validation.password.ValidPasswordValidator;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRequestDtoValidationPasswordTest {

    private Validator validator;
    private ValidPasswordValidator validatorPass;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        validatorPass = new ValidPasswordValidator();
        context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);

    }

    private UserRequestDto baseDto() {
        UserRequestDto dto = new UserRequestDto();
        dto.setName("Juan");
        dto.setLastName("Perez");
        dto.setIdentityDocument("12345678");
        dto.setPhone("+573005698325");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("juan@example.com");

        return dto;
    }


    @Test
    void whenPasswordIsValid_thenValid() {
        UserRequestDto dto = baseDto();
        dto.setPassword("ClaveSegura1!");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenPasswordIsNull_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPassword(null);
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("no puede estar vacía")));
    }

    @Test
    void whenPasswordTooShort_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPassword("Ab1!");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("entre 8 y 64 caracteres")));
    }

    @Test
    void whenPasswordTooLong_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPassword("A".repeat(65) + "1!");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("entre 8 y 64 caracteres")));
    }

    @Test
    void whenPasswordHasNoUppercase_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPassword("clave123!");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("mayúscula")));
    }

    @Test
    void whenPasswordHasNoLowercase_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPassword("CLAVE123!");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("minúscula")));
    }

    @Test
    void whenPasswordHasNoDigit_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPassword("ClaveSegura!");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("número")));
    }

    @Test
    void whenPasswordHasNoSpecialChar_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPassword("ClaveSegura1");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("carácter especial")));
    }

    @Test
    void whenPasswordStartsOrEndsWithSpace_thenValidationFails() {
        UserRequestDto dto = baseDto();
        dto.setPassword(" ClaveSegura1!");
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("espacios al inicio o al final")));
    }

    @Test
    void testNullPassword() {
        assertFalse(validatorPass.isValid(null, context));
    }

    @Test
    void testEmptyPassword() {
        assertFalse(validatorPass.isValid("   ", context));
    }

    @Test
    void testShortPassword() {
        assertFalse(validatorPass.isValid("A1!a", context));
    }

    @Test
    void testLongPassword() {
        assertFalse(validatorPass.isValid("A".repeat(65) + "1!", context));
    }

    @Test
    void testPasswordStartsWithSpace() {
        assertFalse(validatorPass.isValid(" Abcdef1!", context));
    }

    @Test
    void testPasswordEndsWithSpace() {
        assertFalse(validatorPass.isValid("Abcdef1! ", context));
    }

    @Test
    void testPasswordMissingUppercase() {
        assertFalse(validatorPass.isValid("abcdef1!", context));
    }

    @Test
    void testPasswordMissingLowercase() {
        assertFalse(validatorPass.isValid("ABCDEF1!", context));
    }

    @Test
    void testPasswordMissingDigit() {
        assertFalse(validatorPass.isValid("Abcdefgh!", context));
    }

    @Test
    void testPasswordMissingSpecialChar() {
        assertFalse(validatorPass.isValid("Abcdefgh1", context));
    }

    @Test
    void testValidPassword() {
        assertTrue(validatorPass.isValid("Abcdef1!", context));
    }

}
