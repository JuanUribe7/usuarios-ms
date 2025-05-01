package com.users.users_ms.domain.validation;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;

public class Validator {

    private Validator() {
        throw new UnsupportedOperationException("Clase utilitaria, no debe instanciarse.");
    }

    public static void validate(User user, UserPersistencePort port) {
        NameValidator.validate(user.getName());
        if (user.getLastName() != null && !user.getLastName().isEmpty()) {
            NameValidator.validate(user.getLastName());
        }
        DocumentValidator.validate(user.getIdentityDocument());
        EmailValidator.validate(user.getEmail(), port);
        PhoneValidator.validate(user.getPhone());
        PassValidator.validate(user.getPassword());
        AgeValidator.validate(user.getBirthDateAsLocalDate());
    }


    }

