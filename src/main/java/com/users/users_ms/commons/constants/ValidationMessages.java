package com.users.users_ms.commons.constants;

public class ValidationMessages {

    private ValidationMessages() {
        throw new UnsupportedOperationException(ExceptionMessages.UTILITY_CLASS_INSTANTIATION);
    }
    public static final String NAME_NOT_BLANK = "Name cannot be blank";
    public static final String NAME_SIZE = "Name must not exceed 255 characters";
    public static final String LAST_NAME_SIZE = "Last name must not exceed 255 characters";
    public static final String IDENTITY_DOCUMENT_NOT_BLANK = "Identity document cannot be blank";
    public static final String IDENTITY_DOCUMENT_NUMERIC = "Identity document must be numeric";
    public static final String EMAIL_NOT_BLANK = "Email cannot be blank";
    public static final String EMAIL_VALID_FORMAT = "Email must have a valid format";
    public static final String PASSWORD_NOT_BLANK = "Password cannot be blank";
    public static final String PASSWORD_MIN_SIZE = "Password must have at least 8 characters";
    public static final String PHONE_NOT_BLANK = "Phone cannot be blank";
    public static final String PHONE_VALID_FORMAT = "Phone must have a maximum of 13 characters and may include '+'";
    public static final String BIRTH_DATE_NOT_NULL = "Birth date cannot be null";
    public static final String BIRTH_DATE_PAST = "Birth date must be in the past";
    public static final String RESTAURANT_ID_NOT_NULL = "Restaurant ID cannot be null";

    public static final String NAME_VALID_FORMAT = "The name can only contain letters without spaces or special characters.";
    public static final String LAST_NAME_VALID_FORMAT ="The last name can only contain letters without spaces or special characters.";
    public static final String USER_NOT_ADULT = "User must be at least 18 years old.";
}