package com.users.users_ms.application.dto.request;


import com.users.users_ms.commons.constants.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "DTO for creating a client")
public record CreateClientRequestDto(

        @Schema(description = "Client's name", example = "Alice")
        String name,




        @Schema(description = "Client's last name", example = "Brown")
        String lastName,


        @Schema(description = "Client's identity document", example = "456123789")
        String identityDocument,


        @Schema(description = "Client's email", example = "client@example.com")
        String email,


        @Schema(description = "Client's password", example = "securePassword123")
        String password,


        @Schema(description = "Client's phone number", example = "+573005698325")
        String phone,


        @Schema(description = "Client's birth date", example = "2000-10-10")
        LocalDate birthDate
) {}