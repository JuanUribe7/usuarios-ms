package com.users.users_ms.domain.model;


import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.validation.MainValidator;

import java.time.LocalDate;

public class User {

    private Long id;
    private String name;
    private String lastName;
    private String identityDocument;
    private String email;
    private String password;
    private String phone;
    private LocalDate birthDate;
    private Role role;

    public User() {
    }

    public User(Long id, String name, String lastName, String identityDocument, String email, String password, String phone, LocalDate
            birthDate, Role role) {
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

    public User createOwner(UserPersistencePort port) {
        MainValidator.validate(this, port);
        return new User(this.id, name, lastName, identityDocument, email, password, phone, birthDate, Role.OWNER);
    }

}
