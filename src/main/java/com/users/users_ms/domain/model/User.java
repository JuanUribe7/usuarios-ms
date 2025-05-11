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

    public User(Long id, String name, String lastName, String identityDocument, String email, String password, String phone, LocalDate
            birthDate, Role role) {
        validateName(name);
        if(lastName != null && !lastName.isEmpty()){
            validateLastName(lastName);
        }
        validateIdentityDocument(identityDocument);
        validateEmail(email);
        validatePassword(password);
        validatePhone(phone);
        validateBirthDate(birthDate);

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.identityDocument = identityDocument;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.birthDate = birthDate;
        this.role = role;
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



    public boolean isAdult() {
        return Period.between(this.birthDate, LocalDate.now()).getYears() >= 18;
    }



    public User withEncodedPassword(String encodedPassword) {
        return new User(
                this.id,
                this.name,
                this.lastName,
                this.identityDocument,
                this.email,
                encodedPassword,
                this.phone,
                this.birthDate,
                this.role
        );
    }
    public User asRole(Role role) {
        return new User(

                this.id,
                this.name,
                this.lastName,
                this.identityDocument,
                this.email,
                this.password,
                this.phone,
                this.birthDate,
                role
        );
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