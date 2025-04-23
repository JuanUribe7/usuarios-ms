package com.users.users_ms.commons.validation.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {
    String message() default "Formato de email invalido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}