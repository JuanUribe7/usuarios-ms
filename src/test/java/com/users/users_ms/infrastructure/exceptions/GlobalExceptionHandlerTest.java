package com.users.users_ms.infrastructure.exceptions;

import com.users.users_ms.domain.exceptions.InvalidEmailException;
import com.users.users_ms.domain.exceptions.InvalidIdentityDocumentException;
import com.users.users_ms.domain.exceptions.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleValidationException_ReturnsFieldErrors() {
        // Arrange: crear BindingResult con errores
        Object target = new Object();
        BindingResult bindingResult = new BeanPropertyBindingResult(target, "target");
        bindingResult.addError(new FieldError("target", "field1", "message1"));
        bindingResult.addError(new FieldError("target", "field2", "message2"));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleValidationException(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> body = response.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        assertEquals("message1", body.get("field1"));
        assertEquals("message2", body.get("field2"));
    }

    @Test
    void handleInvalidEmail_ReturnsBadRequest() {
        // Arrange
        InvalidEmailException ex = new InvalidEmailException("Invalid email");

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleInvalidEmail(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email", response.getBody().get("error"));
    }

    @Test
    void handleInvalidIdentityDocument_ReturnsBadRequest() {
        // Arrange
        InvalidIdentityDocumentException ex = new InvalidIdentityDocumentException("Invalid ID");

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleInvalidId(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid ID", response.getBody().get("error"));
    }

    @Test
    void handleUnauthorized_ReturnsUnauthorized() {
        // Arrange
        UnauthorizedException ex = new UnauthorizedException("Not authorized");

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleUnauthorized(ex);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Not authorized", response.getBody().get("error"));
    }

    @Test
    void handleIllegalArgument_ReturnsBadRequest() {
        // Arrange
        IllegalArgumentException ex = new IllegalArgumentException("Bad arg");

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleIllegalArgument(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad arg", response.getBody().get("error"));
    }

    @Test
    void handleGeneric_ReturnsInternalServerError() {
        // Arrange
        Exception ex = new Exception("some error");

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleGeneric(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error interno: some error", response.getBody().get("error"));
    }
}
