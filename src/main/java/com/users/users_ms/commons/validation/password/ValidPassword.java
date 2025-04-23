package com.users.users_ms.commons.validation.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "El número de teléfono es inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}