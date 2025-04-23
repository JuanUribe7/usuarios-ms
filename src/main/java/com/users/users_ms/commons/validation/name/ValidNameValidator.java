package com.users.users_ms.commons.validation.name;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidNameValidator implements ConstraintValidator<ValidName, String> {

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null || name.trim().isEmpty()) {
            buildViolation(context, "El nombre no puede estar vacío");
            return false;
        }

        // Permite letras (mayús/minús) y espacios
        if (!name.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
            buildViolation(context, "El nombre solo puede contener letras y espacios");
            return false;
        }

        // Evitar múltiples espacios seguidos
        if (name.contains("  ")) {
            buildViolation(context, "El nombre no puede tener espacios dobles");
            return false;
        }

        // No comenzar ni terminar con espacio
        if (name.startsWith(" ") || name.endsWith(" ")) {
            buildViolation(context, "El nombre no puede comenzar ni terminar con espacio");
            return false;
        }

        // Longitud mínima
        if (name.length() < 2) {
            buildViolation(context, "El nombre debe tener al menos 2 caracteres");
            return false;
        }

        return true;
    }

    private void buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
