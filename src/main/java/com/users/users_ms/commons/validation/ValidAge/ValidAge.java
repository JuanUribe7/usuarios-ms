package com.users.users_ms.commons.validation.ValidAge;



import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidAgeValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAge {
    String message() default "Edad no válida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
