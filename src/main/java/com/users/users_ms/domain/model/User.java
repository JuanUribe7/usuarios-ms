package com.users.users_ms.domain.model;



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
}