package com.users.users_ms.domain.model;

public enum Role {
    ADMIN, OWNER, EMPLOYEE, CLIENT;

    public static boolean isAllowed(String role) {
        try {
            Role.valueOf(role.toUpperCase());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
