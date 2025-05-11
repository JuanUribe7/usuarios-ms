package com.users.users_ms.application.dto.request;

import com.users.users_ms.commons.constants.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "DTO for creating an owner")
public record CreateOwnerRequestDto(


        @Size(max = 255, message = ValidationMessages.NAME_SIZE)
        @Schema(description = "Owner's name", example = "John")
        String name,

        @Schema(description = "Owner's last name", example = "Doe")
        String lastName,


        @Schema(description = "Owner's identity document", example = "123456789")
        String identityDocument,


        @Schema(description = "Owner's email", example = "owner@example.com")
        String email,

        @Schema(description = "Owner's password", example = "securePassword123")
        String password,


        @Schema(description = "Owner's phone number", example = "+573005698325")
        String phone,


        @Schema(description = "Owner's birth date", example = "1990-01-01")
        LocalDate birthDate
) {}