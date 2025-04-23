package com.users.users_ms.commons.validation.document;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidIdentityDocumentValidator implements ConstraintValidator<ValidIdentityDocument, String> {

    @Override
    public boolean isValid(String document, ConstraintValidatorContext context) {
        if (document == null || document.trim().isEmpty()) {
            buildViolation(context, "El documento de identidad no puede estar vacío");
            return false;
        }

        if (!document.matches("^\\d+$")) {
            buildViolation(context, "El documento de identidad debe contener solo números");
            return false;
        }

        if (document.length() < 8 || document.length() > 11) {
            buildViolation(context, "El documento de identidad debe tener entre 8 y 11 dígitos");
            return false;
        }

        if (document.startsWith("0")) {
            buildViolation(context, "El documento de identidad no puede comenzar con 0");
            return false;
        }

        return true;
    }

    private void buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
