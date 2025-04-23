package com.users.users_ms.commons.validation.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            buildViolation(context, "El correo electrónico no puede estar vacío");
            return false;
        }

        if (email.length() > 254) {
            buildViolation(context, "El correo electrónico no puede superar los 254 caracteres");
            return false;
        }

        // No espacios permitidos
        if (email.contains(" ")) {
            buildViolation(context, "El correo electrónico no puede contener espacios");
            return false;
        }

        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            buildViolation(context, "Formato de correo electrónico no válido");
            return false;
        }

        String domain = email.substring(email.indexOf('@') + 1);
        String[] domainParts = domain.split("\\.");

        for (String part : domainParts) {
            if (part.startsWith("-") || part.endsWith("-")) {
                buildViolation(context, "El dominio del correo no puede comenzar ni terminar con '-'");
                return false;
            }
        }

        return true;
    }

    private void buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}