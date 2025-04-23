package com.users.users_ms.commons.validation.name;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = ValidNameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
    String message() default "El nombre solo debe contener letras y espacios, sin números ni símbolos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
