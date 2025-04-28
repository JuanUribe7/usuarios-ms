package com.users.users_ms.domain.validation;

import com.users.users_ms.domain.exceptions.InvalidIdentityDocumentException;
import com.users.users_ms.domain.ports.out.IUserPersistencePort;

public class DocumentValidator {

    private DocumentValidator() {
        throw new UnsupportedOperationException("Clase utilitaria, no debe instanciarse.");
    }


    public static void validate(String document, IUserPersistencePort IUserPersistencePort) {
        if (IUserPersistencePort.findByIdentityDocument(document).isPresent()){
            throw new InvalidIdentityDocumentException("El documento de identidad ya está registrado.");
        }
        if (document == null || document.trim().isEmpty()) {
            throw new IllegalArgumentException("El documento de identidad no puede estar vacío");

        }

        if (!document.matches("^\\d+$")) {
            throw new IllegalArgumentException("El documento de identidad debe contener solo números");

        }

        if (document.length() < 8 || document.length() > 11) {
            throw new IllegalArgumentException("El documento de identidad debe tener entre 8 y 11 dígitos");
        }

        if (document.startsWith("0")) {
            throw new IllegalArgumentException("El documento de identidad no puede comenzar con 0");
        }

}}
