package com.users.users_ms.domain.validation;


public class PassValidator {

    private PassValidator() {
        throw new UnsupportedOperationException("Clase utilitaria, no debe instanciarse.");
    }



    public static void validate(String password){
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La clave no puede estar vacía");
        }

        if (password.length() < 8 || password.length() > 64) {
            throw new IllegalArgumentException("La clave debe tener entre 8 y 64 caracteres");

        }

        if (Character.isWhitespace(password.charAt(0)) || Character.isWhitespace(password.charAt(password.length() - 1))) {
            throw new IllegalArgumentException("La clave no puede tener espacios al inicio o al final");


        }

        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("La clave debe contener al menos una letra mayúscula");

        }

        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("La clave debe contener al menos una letra minúscula");

        }

        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("La clave debe contener al menos un número");

        }

        if (!password.matches(".*[!@#$%^&*()_+\\-={}\\[\\]|;:'\",.<>/?].*")) {
            throw new IllegalArgumentException("La clave debe contener al menos un carácter especial");
        }

    }








}
