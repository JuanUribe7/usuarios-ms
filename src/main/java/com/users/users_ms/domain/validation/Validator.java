package com.users.users_ms.domain.validation;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;

public class Validator {

    private Validator() {
        throw new UnsupportedOperationException("Clase utilitaria, no debe instanciarse.");
    }


    public static void validate(User user , UserPersistencePort userPersistencePort) {
        AgeValidator.validate(user.getBirthDate());
        DocumentValidator.validate(user.getIdentityDocument(),userPersistencePort);
        EmailValidator.validate(user.getEmail(),userPersistencePort );
        NameValidator.validate(user.getName());
        PhoneValidator.validate(user.getPhone());
        PassValidator.validate(user.getPassword());
        NameValidator.validate(user.getLastName());


    }
}
