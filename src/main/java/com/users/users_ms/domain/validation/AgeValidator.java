package com.users.users_ms.domain.validation;


import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class AgeValidator {

    private AgeValidator() {
        throw new UnsupportedOperationException("Clase utilitaria, no debe instanciarse.");
    }


    public static void validate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía");

        }

        LocalDate today = LocalDate.now();

        if (birthDate.isAfter(today)) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar en el futuro");

        }

        if (birthDate.isBefore(LocalDate.of(1900, 1, 1))) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser anterior a 1900");
        }

        if (birthDate.plusYears(18).isAfter(today)) {
            throw new IllegalArgumentException("Debe ser mayor de edad (18+)");
        }


}}