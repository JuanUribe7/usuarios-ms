package com.users.users_ms.domain.validation;

import com.users.users_ms.domain.ports.out.UserPersistencePort;

public class EmailValidator {

    private EmailValidator() {
        throw new UnsupportedOperationException("Clase utilitaria, no debe instanciarse.");
    }

    public static void validate(String email, UserPersistencePort userPersistencePort) {

        if (userPersistencePort.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email ya registrado");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío");
        }

        if (email.length() > 254) {
            throw new IllegalArgumentException("El correo electrónico no puede superar los 254 caracteres");
        }

        if (email.contains(" ")) {
            throw new IllegalArgumentException("El correo electrónico no puede contener espacios");
        }

        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Formato de correo electrónico no válido");

        }

        String domain = email.substring(email.indexOf('@') + 1);
        String[] domainParts = domain.split("\\.");

        for (String part : domainParts) {
            if (part.startsWith("-") || part.endsWith("-")) {
                throw new IllegalArgumentException("El dominio del correo no puede comenzar ni terminar con '-'");
            }
        }
    }
}
