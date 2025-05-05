package com.users.users_ms.commons.constants;

public class ExceptionMessages {
    private ExceptionMessages() {
        throw new UnsupportedOperationException(UTILITY_CLASS_INSTANTIATION);
    }
    public static final String EMAIL_ALREADY_REGISTERED = "Email is already registered.";

    public static final String NAME_INVALID = "Name must contain only letters and spaces";
    public static final String NAME_TOO_SHORT = "Name must have at least 2 characters";
    public static final String NAME_DOUBLE_SPACE = "Name cannot contain double spaces";
    public static final String NAME_TRIM_ERROR = "Name cannot start or end with a space";

    public static final String PHONE_EMPTY = "Phone number cannot be empty";
    public static final String PHONE_INVALID_FORMAT = "Phone number must be 10-13 digits with optional '+' at start";
    public static final String PHONE_PLUS_ERROR = "'+' can only appear once and only at the beginning";
    public static final String PHONE_INVALID_PREFIX = "Phone number cannot start with '00'";

    public static final String EMAIL_EMPTY = "Email cannot be empty";
    public static final String EMAIL_TOO_LONG = "Email cannot exceed 254 characters";
    public static final String EMAIL_SPACES = "Email cannot contain spaces";
    public static final String EMAIL_INVALID_FORMAT = "Invalid email format";
    public static final String EMAIL_DOMAIN_INVALID = "Email domain parts cannot start or end with hyphens";


    public static final String PASSWORD_EMPTY = "Password cannot be empty";
    public static final String PASSWORD_LENGTH = "Password must be between 8 and 64 characters";
    public static final String PASSWORD_SPACES = "Password cannot start or end with whitespace";
    public static final String PASSWORD_MISSING_UPPERCASE = "Password must contain at least one uppercase letter";
    public static final String PASSWORD_MISSING_LOWERCASE = "Password must contain at least one lowercase letter";
    public static final String PASSWORD_MISSING_NUMBER = "Password must contain at least one digit";
    public static final String PASSWORD_MISSING_SYMBOL = "Password must contain at least one special character";

    public static final String DOCUMENT_EMPTY = "Identity document cannot be empty";
    public static final String DOCUMENT_NON_NUMERIC = "Identity document must contain only numbers";
    public static final String DOCUMENT_LENGTH = "Identity document must be between 8 and 11 digits";
    public static final String DOCUMENT_INVALID_START = "Identity document cannot start with 0";

    public static final String BIRTHDATE_EMPTY = "Birth date cannot be null";
    public static final String BIRTHDATE_FUTURE = "Birth date cannot be in the future";
    public static final String BIRTHDATE_TOO_OLD = "Birth date cannot be before 1900";
    public static final String BIRTHDATE_NOT_ADULT = "User must be at least 18 years old";
    public static final String UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated";
    public static final String INVALID_ROLE_ADMIN = "Invalid role, only 'ADMIN' is allowed";

}
