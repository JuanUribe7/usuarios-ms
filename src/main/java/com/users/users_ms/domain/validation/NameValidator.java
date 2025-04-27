package com.users.users_ms.domain.validation;




public class NameValidator  {
    private NameValidator() {
        throw new UnsupportedOperationException("Clase utilitaria, no debe instanciarse.");
    }

    public static void validate(String name){

        if (!name.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y espacios");
        }


        if (name.contains("  ")) {
            throw new IllegalArgumentException("El nombre no puede tener espacios dobles");
        }


        if (name.startsWith(" ") || name.endsWith(" ")) {
            throw new IllegalArgumentException("El nombre no puede comenzar ni terminar con espacio");
        }


        if (name.length() < 2) {
            throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres");
        }

    }

    }
