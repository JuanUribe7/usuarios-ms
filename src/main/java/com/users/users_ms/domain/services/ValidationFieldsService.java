package com.users.users_ms.domain.services;

import com.users.users_ms.commons.constants.ValidationMessages;
import com.users.users_ms.domain.helper.FieldValidator;

import java.time.LocalDate;

public class ValidationFieldsService {

    public void validateName(String name) {
        FieldValidator.validateNotBlank(name, ValidationMessages.NAME_NOT_BLANK);
        FieldValidator.validatePattern(name, "^[a-zA-Z]+$", ValidationMessages.NAME_VALID_FORMAT);
        FieldValidator.validateMaxLength(name, 255, ValidationMessages.NAME_SIZE);
    }

    public void validateLastName(String lastName) {
        FieldValidator.validatePattern(lastName, "^[a-zA-Z]*$", ValidationMessages.LAST_NAME_VALID_FORMAT);
        FieldValidator.validateMaxLength(lastName, 255, ValidationMessages.LAST_NAME_SIZE);
    }

    public void validateIdentityDocument(String identityDocument) {
        FieldValidator.validateNotBlank(identityDocument, ValidationMessages.IDENTITY_DOCUMENT_NOT_BLANK);
        FieldValidator.validatePattern(identityDocument, "\\d+", ValidationMessages.IDENTITY_DOCUMENT_NUMERIC);
    }

    public void validateEmail(String email) {
        FieldValidator.validateNotBlank(email, ValidationMessages.EMAIL_NOT_BLANK);
        FieldValidator.validatePattern(email, "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", ValidationMessages.EMAIL_VALID_FORMAT);
    }

    public void validatePassword(String password) {
        FieldValidator.validateNotBlank(password, ValidationMessages.PASSWORD_NOT_BLANK);
        FieldValidator.validateMinLength(password, 8, ValidationMessages.PASSWORD_MIN_SIZE);
    }

    public void validatePhone(String phone) {
        FieldValidator.validateNotBlank(phone, ValidationMessages.PHONE_NOT_BLANK);
        FieldValidator.validatePattern(phone, "(\\+\\d{1,12}|\\d{1,12})", ValidationMessages.PHONE_VALID_FORMAT);
    }

    public void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null || !birthDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(ValidationMessages.BIRTH_DATE_PAST);
        }
    }
}