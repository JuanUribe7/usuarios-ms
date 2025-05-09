package com.users.users_ms.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Schema(description = "DTO used to register a new user")
public record UserRequestDto(
        @Schema(description = "User's first name", example = "Juan")
        @NotBlank(message = "Name cannot be empty")
        String name,

        @Schema(description = "User's last name", example = "Uribe")
        String lastName,

        @Schema(description = "User's identity document", example = "12345678")
        String identityDocument,

        @Schema(description = "User's email address", example = "juan@example.com")
        String email,

        @Schema(description = "User's password", example = "StrongPass123!")
        String password,

        @Schema(description = "User's phone number", example = "3001234567")
        String phone,

        @Schema(description = "User's birth date", example = "2000-01-01")
        LocalDate birthDate,

        @Schema(description = "Role ID associated with the user", example = "2")
        Long roleId
) {}