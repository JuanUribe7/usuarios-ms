package com.users.users_ms.commons.validation.password;



import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.trim().isEmpty()) {
            buildViolation(context, "La clave no puede estar vacía");
            return false;
        }

        if (password.length() < 8 || password.length() > 64) {
            buildViolation(context, "La clave debe tener entre 8 y 64 caracteres");
            return false;
        }

        if (Character.isWhitespace(password.charAt(0)) || Character.isWhitespace(password.charAt(password.length() - 1))) {
            buildViolation(context, "La clave no puede tener espacios al inicio o al final");
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            buildViolation(context, "La clave debe contener al menos una letra mayúscula");
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            buildViolation(context, "La clave debe contener al menos una letra minúscula");
            return false;
        }

        if (!password.matches(".*\\d.*")) {
            buildViolation(context, "La clave debe contener al menos un número");
            return false;
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-={}\\[\\]|;:'\",.<>/?].*")) {
            buildViolation(context, "La clave debe contener al menos un carácter especial");
            return false;
        }

        return true;
    }

    private void buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
