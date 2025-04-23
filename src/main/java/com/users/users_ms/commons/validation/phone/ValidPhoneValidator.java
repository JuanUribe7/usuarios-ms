package com.users.users_ms.commons.validation.phone;



import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPhoneValidator implements ConstraintValidator<ValidPhone, String> {

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null || phone.trim().isEmpty()) {
            buildViolation(context, "El número de teléfono no puede estar vacío");
            return false;
        }

        // Validar que '+' esté solo al inicio y no repetido
        if (phone.indexOf('+') > 0 || phone.chars().filter(ch -> ch == '+').count() > 1) {
            buildViolation(context, "El '+' solo puede estar al inicio y una sola vez");
            return false;
        }

        // Remover '+' para validar número
        String numberOnly = phone.startsWith("+") ? phone.substring(1) : phone;

        // No puede comenzar con 00 después de quitar '+'
        if (numberOnly.startsWith("00")) {
            buildViolation(context, "El número no puede comenzar con '00'");
            return false;
        }

        // Validar longitud y caracteres permitidos
        if (!numberOnly.matches("^[0-9]{10,13}$")) {
            buildViolation(context, "El número debe tener entre 10 y 13 caracteres y contener solo dígitos, con un '+' opcional al inicio");
            return false;
        }

        return true;
    }


    private void buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
