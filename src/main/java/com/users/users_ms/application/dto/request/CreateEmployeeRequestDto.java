package com.users.users_ms.application.dto.request;

import com.users.users_ms.commons.constants.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "DTO for creating an employee")
public record CreateEmployeeRequestDto(
        @NotBlank(message = ValidationMessages.NAME_NOT_BLANK)
        @Size(max = 255, message = ValidationMessages.NAME_SIZE)
        @Pattern(regexp = "^[a-zA-Z]+$", message = ValidationMessages.NAME_VALID_FORMAT)
        @Schema(description = "Employee's name", example = "Jane")
        String name,


        @Pattern(regexp = "^[a-zA-Z]*$", message = ValidationMessages.LAST_NAME_VALID_FORMAT)
        @Size(max = 255, message = ValidationMessages.LAST_NAME_SIZE)
        @Schema(description = "Employee's last name", example = "Smith")
        String lastName,


        @Schema(description = "Employee's identity document", example = "987654321")
        String identityDocument,


        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = ValidationMessages.EMAIL_VALID_FORMAT)
        @Schema(description = "Employee's email", example = "employee@example.com")
        String email,


        @Schema(description = "Employee's password", example = "securePassword123")
        String password,


        @Schema(description = "Employee's phone number", example = "+573005698325")
        String phone,


        @Schema(description = "Employee's birth date", example = "1995-05-15")
        LocalDate birthDate,


        @Schema(description = "Restaurant ID where the employee works", example = "1")
        Long restaurantId
) {}