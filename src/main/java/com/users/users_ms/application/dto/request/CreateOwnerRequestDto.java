package com.users.users_ms.application.dto.request;

import com.users.users_ms.commons.constants.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "DTO for creating an owner")
public record CreateOwnerRequestDto(
        @NotBlank(message = ValidationMessages.NAME_NOT_BLANK)
        @Pattern(regexp = "^[a-zA-Z]+$", message = ValidationMessages.NAME_VALID_FORMAT)
        @Size(max = 255, message = ValidationMessages.NAME_SIZE)
        @Schema(description = "Owner's name", example = "John")
        String name,

        @Pattern(regexp = "^[a-zA-Z]+$", message = ValidationMessages.LAST_NAME_VALID_FORMAT)
        @Size(max = 255, message = ValidationMessages.LAST_NAME_SIZE)
        @Schema(description = "Owner's last name", example = "Doe")
        String lastName,

        @NotBlank(message = ValidationMessages.IDENTITY_DOCUMENT_NOT_BLANK)
        @Pattern(regexp = "\\d+", message = ValidationMessages.IDENTITY_DOCUMENT_NUMERIC)
        @Schema(description = "Owner's identity document", example = "123456789")
        String identityDocument,

        @NotBlank(message = ValidationMessages.EMAIL_NOT_BLANK)
        @Email(message = ValidationMessages.EMAIL_VALID_FORMAT)
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = ValidationMessages.EMAIL_VALID_FORMAT)
        @Schema(description = "Owner's email", example = "owner@example.com")
        String email,

        @NotBlank(message = ValidationMessages.PASSWORD_NOT_BLANK)
        @Size(min = 8, message = ValidationMessages.PASSWORD_MIN_SIZE)
        @Schema(description = "Owner's password", example = "securePassword123")
        String password,

        @NotBlank(message = ValidationMessages.PHONE_NOT_BLANK)
        @Pattern(regexp = "(\\+\\d{1,12}|\\d{1,12})", message = ValidationMessages.PHONE_VALID_FORMAT)
        @Schema(description = "Owner's phone number", example = "+573005698325")
        String phone,

        @NotNull(message = ValidationMessages.BIRTH_DATE_NOT_NULL)
        @Past(message = ValidationMessages.BIRTH_DATE_PAST)
        @Schema(description = "Owner's birth date", example = "1990-01-01")
        LocalDate birthDate
) {}