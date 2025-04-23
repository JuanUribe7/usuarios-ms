package com.users.users_ms.commons.validation.document;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidIdentityDocumentValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIdentityDocument {
    String message() default "El documento de identidad debe ser numérico y válido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}