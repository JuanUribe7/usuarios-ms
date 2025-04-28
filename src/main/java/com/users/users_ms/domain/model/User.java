package com.users.users_ms.domain.model;

import java.time.LocalDate;

public class User {
    private Long id;
    private String name;
    private String lastName;
    private String identityDocument;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private String password;
    private Role role;
    private Long restaurantId;




    public User() {
    }
    public User(Long id, String name, String lastName, String identityDocument, String phone, LocalDate birthDate, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.identityDocument = identityDocument;
        this.phone = phone;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.role = role;


    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(String identityDocument) {
        this.identityDocument = identityDocument;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
