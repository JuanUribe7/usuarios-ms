package com.users.users_ms.domain.validation;


public class PhoneValidator{

    private PhoneValidator() {
        throw new UnsupportedOperationException("Clase utilitaria, no debe instanciarse.");
    }

    public static void validate(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de teléfono no puede estar vacío");
        }


        if (phone.indexOf('+') > 0 || phone.chars().filter(ch -> ch == '+').count() > 1) {
            throw new IllegalArgumentException("El '+' solo puede estar al inicio y una sola vez");
        }

        // Remover '+' para validar número
        String numberOnly = phone.startsWith("+") ? phone.substring(1) : phone;


        if (numberOnly.startsWith("00")) {
            throw new IllegalArgumentException("El número no puede comenzar con '00'");
        }


        if (!numberOnly.matches("^[0-9]{10,13}$")) {
            throw new IllegalArgumentException("El número debe tener entre 10 y 13 caracteres y contener solo dígitos, con un '+' opcional al inicio");
        }
    }


}
