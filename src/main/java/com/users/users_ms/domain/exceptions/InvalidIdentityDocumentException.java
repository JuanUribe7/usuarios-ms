package com.users.users_ms.domain.exceptions;

public class InvalidIdentityDocumentException extends RuntimeException {
    public InvalidIdentityDocumentException(String message) {
        super(message);
    }
}
