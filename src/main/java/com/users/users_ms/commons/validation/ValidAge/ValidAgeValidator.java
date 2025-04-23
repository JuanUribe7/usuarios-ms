package com.users.users_ms.commons.validation.ValidAge;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidAgeValidator implements ConstraintValidator<ValidAge, LocalDate> {

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) {
            buildViolation(context, "La fecha de nacimiento es obligatoria");
            return false;
        }

        LocalDate today = LocalDate.now();

        if (birthDate.isAfter(today)) {
            buildViolation(context, "La fecha no puede estar en el futuro");
            return false;
        }

        if (birthDate.isBefore(LocalDate.of(1900, 1, 1))) {
            buildViolation(context, "La fecha es demasiado antigua");
            return false;
        }




        if (birthDate.plusYears(18).isAfter(today)) {
            buildViolation(context, "Debe ser mayor de edad (18+)");
            return false;
        }

        return true;
    }

    private void buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}