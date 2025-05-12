package com.users.users_ms.domain.services;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.ValidationFields;

public class UserValidationService implements ValidationFields {
    private final ValidationFieldsService validationFieldsService;

    public UserValidationService(ValidationFieldsService validationFieldsService) {
        this.validationFieldsService = validationFieldsService;
    }

    @Override
    public void validateUser(User user) {
        validationFieldsService.validateName(user.getName());
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            validationFieldsService.validateLastName(user.getLastName());
        }

        validationFieldsService.validateIdentityDocument(user.getIdentityDocument());
        validationFieldsService.validateEmail(user.getEmail());
        validationFieldsService.validatePassword(user.getPassword());
        validationFieldsService.validatePhone(user.getPhone());
        validationFieldsService.validateBirthDate(user.getBirthDate());
    }
}
