package com.users.users_ms.domain.validation;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;

public class Validator {


    public static void validate(User user , UserPersistencePort userPersistencePort) {
        AgeValidator.validate(user.getBirthDate());
        DocumentValidator.validate(user.getIdentityDocument(),userPersistencePort);
        EmailValidator.validate(user.getEmail(),userPersistencePort );
        NameValidator.validate(user.getName());
        NameValidator.validate(user.getLastName());
        PhoneValidator.validate(user.getPhone());
        PassValidator.validate(user.getPassword());
        NameValidator.validate(user.getLastName());
    }
}
