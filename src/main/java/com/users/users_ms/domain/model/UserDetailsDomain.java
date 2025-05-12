package com.users.users_ms.domain.model;

import java.util.Set;

public class UserDetailsDomain {
    private final String email;
    private final Set<String> roles;

    public UserDetailsDomain(String email, Set<String> roles) {
        this.email = email;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getRoles() {
        return roles;
    }
}