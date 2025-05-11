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

    private User(Builder builder) {
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
}