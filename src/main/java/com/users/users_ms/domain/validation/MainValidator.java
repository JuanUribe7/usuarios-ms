package com.users.users_ms.domain.validation;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;

public class MainValidator {

    private MainValidator() {
        throw new UnsupportedOperationException(ExceptionMessages.UTILITY_CLASS_INSTANTIATION);
    }

    public static void validate(User user, UserPersistencePort port) {
        FieldValidationUtils.validateName(user.getName());

        if (user.getLastName() != null && !user.getLastName().isEmpty()) {
            FieldValidationUtils.validateName(user.getLastName());
        }

        FieldValidationUtils.validateDocument(user.getIdentityDocument());
        FieldValidationUtils.validateEmail(user.getEmail(), port);
        FieldValidationUtils.validatePhone(user.getPhone());
        FieldValidationUtils.validatePassword(user.getPassword());
        FieldValidationUtils.validateBirthDate(user.getBirthDate());
    }
}