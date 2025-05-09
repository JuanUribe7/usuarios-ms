package com.users.users_ms.domain.validation;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.InvalidFieldException;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.validation.core.*;

import java.time.LocalDate;

public class FieldValidationUtils {

    private FieldValidationUtils() {
        throw new UnsupportedOperationException(ExceptionMessages.UTILITY_CLASS_INSTANTIATION);
    }

    public static void validateName(String name) {
        EmptyValidator.requireNonEmpty(name, ExceptionMessages.NAME_INVALID);
        RegexValidator.validate(name, RegexValidator.Field.NAME, ExceptionMessages.NAME_INVALID);
        PrefixValidator.mustNotHaveDoubleSpaces(name, ExceptionMessages.NAME_DOUBLE_SPACE);
        PrefixValidator.mustNotHaveTrimSpaces(name, ExceptionMessages.NAME_TRIM_ERROR);
        LengthValidator.validate(name, 2, 255, ExceptionMessages.NAME_TOO_SHORT);
    }

    public static void validateDocument(String document) {
        EmptyValidator.requireNonEmpty(document, ExceptionMessages.DOCUMENT_EMPTY);
        RegexValidator.validate(document, RegexValidator.Field.DOCUMENT, ExceptionMessages.DOCUMENT_NON_NUMERIC);
        LengthValidator.validate(document, 8, 11, ExceptionMessages.DOCUMENT_LENGTH);
        PrefixValidator.mustNotStartWith(document, "0", ExceptionMessages.DOCUMENT_INVALID_START);
    }

    public static void validateEmail(String email, UserPersistencePort port) {
        UniquenessValidator.validate(()-> port.existsByEmail(email),"email", email);
        EmptyValidator.requireNonEmpty(email, ExceptionMessages.EMAIL_EMPTY);
        if (email.length() > 254) {
            throw new InvalidFieldException(ExceptionMessages.EMAIL_TOO_LONG);
        }
        PrefixValidator.mustNotContain(email, " ", ExceptionMessages.EMAIL_SPACES);
        RegexValidator.validate(email, RegexValidator.Field.EMAIL, ExceptionMessages.EMAIL_INVALID_FORMAT);

        String domain = email.substring(email.indexOf('@') + 1);
        for (String part : domain.split("\\.")) {
            if (part.startsWith("-") || part.endsWith("-")) {
                throw new InvalidFieldException(ExceptionMessages.EMAIL_DOMAIN_INVALID);
            }
        }
    }

    public static void validatePhone(String phone) {
        EmptyValidator.requireNonEmpty(phone, ExceptionMessages.PHONE_EMPTY);
        if (!phone.startsWith("+")) {
            throw new InvalidFieldException(ExceptionMessages.PHONE_PLUS_ERROR);
        }
        if (phone.indexOf('+') > 0 || phone.chars().filter(ch -> ch == '+').count() > 1) {
            throw new InvalidFieldException(ExceptionMessages.PHONE_PLUS_ERROR);
        }
        String numberOnly = phone.startsWith("+") ? phone.substring(1) : phone;
        PrefixValidator.mustNotStartWith(numberOnly, "00", ExceptionMessages.PHONE_INVALID_PREFIX);
        RegexValidator.validate(numberOnly, RegexValidator.Field.PHONE, ExceptionMessages.PHONE_INVALID_FORMAT);
    }

    public static void validatePassword(String password) {
        PasswordValidator.validate(password);
    }

    public static void validateBirthDate(LocalDate birthDate) {
        DateValidator.validateAdultBirthDate(birthDate);
    }
}