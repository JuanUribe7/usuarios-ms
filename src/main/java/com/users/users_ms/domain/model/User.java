package com.users.users_ms.domain.model;

import com.users.users_ms.commons.constants.ValidationMessages;
import com.users.users_ms.domain.helper.FieldValidator;

import java.time.LocalDate;
import java.time.Period;

public class User {

    private final Long id;
    private final String name;
    private final String lastName;
    private final String identityDocument;
    private final String email;
    private final String password;
    private final String phone;
    private final LocalDate birthDate;
    private final Role role;

    private User(Builder builder) {

        validateName(builder.name);
        validateLastName(builder.lastName);
        validateIdentityDocument(builder.identityDocument);
        validateEmail(builder.email);
        validatePassword(builder.password);
        validatePhone(builder.phone);
        validateBirthDate(builder.birthDate);
        this.id = builder.id;
        this.name = builder.name;
        this.lastName = builder.lastName;
        this.identityDocument = builder.identityDocument;
        this.email = builder.email;
        this.password = builder.password;
        this.phone = builder.phone;
        this.birthDate = builder.birthDate;
        this.role = builder.role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdentityDocument() {
        return identityDocument;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Role getRole() {
        return role;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String lastName;
        private String identityDocument;
        private String email;
        private String password;
        private String phone;
        private LocalDate birthDate;
        private Role role;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder identityDocument(String identityDocument) {
            this.identityDocument = identityDocument;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public boolean isAdult() {
        return Period.between(this.birthDate, LocalDate.now()).getYears() >= 18;
    }

    public User withEncodedPassword(String encodedPassword) {
        return new Builder()
                .id(this.id)
                .name(this.name)
                .lastName(this.lastName)
                .identityDocument(this.identityDocument)
                .email(this.email)
                .password(encodedPassword)
                .phone(this.phone)
                .birthDate(this.birthDate)
                .role(this.role)
                .build();
    }

    public User asRole(Role role) {
        return new Builder()
                .id(this.id)
                .name(this.name)
                .lastName(this.lastName)
                .identityDocument(this.identityDocument)
                .email(this.email)
                .password(this.password)
                .phone(this.phone)
                .birthDate(this.birthDate)
                .role(role)
                .build();
    }

    private void validateName(String name) {
        FieldValidator.validateNotBlank(name, ValidationMessages.NAME_NOT_BLANK);
        FieldValidator.validatePattern(name, "^[a-zA-Z]+$", ValidationMessages.NAME_VALID_FORMAT);
        FieldValidator.validateMaxLength(name, 255, ValidationMessages.NAME_SIZE);
    }

    private void validateLastName(String lastName) {
        FieldValidator.validatePattern(lastName, "^[a-zA-Z]*$", ValidationMessages.LAST_NAME_VALID_FORMAT);
        FieldValidator.validateMaxLength(lastName, 255, ValidationMessages.LAST_NAME_SIZE);
    }

    private void validateIdentityDocument(String identityDocument) {
        FieldValidator.validateNotBlank(identityDocument, ValidationMessages.IDENTITY_DOCUMENT_NOT_BLANK);
        FieldValidator.validatePattern(identityDocument, "\\d+", ValidationMessages.IDENTITY_DOCUMENT_NUMERIC);
    }

    private void validateEmail(String email) {
        FieldValidator.validateNotBlank(email, ValidationMessages.EMAIL_NOT_BLANK);
        FieldValidator.validatePattern(email, "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", ValidationMessages.EMAIL_VALID_FORMAT);
    }

    private void validatePassword(String password) {
        FieldValidator.validateNotBlank(password, ValidationMessages.PASSWORD_NOT_BLANK);
        FieldValidator.validateMinLength(password, 8, ValidationMessages.PASSWORD_MIN_SIZE);
    }

    private void validatePhone(String phone) {
        FieldValidator.validateNotBlank(phone, ValidationMessages.PHONE_NOT_BLANK);
        FieldValidator.validatePattern(phone, "(\\+\\d{1,12}|\\d{1,12})", ValidationMessages.PHONE_VALID_FORMAT);
    }

    private void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null || !birthDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(ValidationMessages.BIRTH_DATE_PAST);
        }
    }
}