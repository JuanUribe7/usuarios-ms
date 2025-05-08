package com.users.users_ms.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Schema(description = "DTO used to register a new user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @Schema(description = "User's first name", example = "Juan")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Schema(description = "User's last name", example = "Uribe")
    private String lastName;

    @Schema(description = "User's identity document", example = "12345678")
    private String identityDocument;

    @Schema(description = "User's email address", example = "juan@example.com")
    private String email;

    @Schema(description = "User's password", example = "StrongPass123!")
    private String password;

    @Schema(description = "User's phone number", example = "3001234567")
    private String phone;

    @Schema(description = "User's birth date", example = "2000-01-01")
    private LocalDate birthDate;

    @Schema(description = "Role ID associated with the user", example = "2")
    private Long roleId;
}