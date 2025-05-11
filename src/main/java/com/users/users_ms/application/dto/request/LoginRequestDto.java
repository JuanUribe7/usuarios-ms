package com.users.users_ms.application.dto.request;

import com.users.users_ms.commons.constants.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Schema(description = "DTO for user login")
public record LoginRequestDto(
        @NotBlank(message = ValidationMessages.EMAIL_NOT_BLANK)
        @Schema(description = "User's email", example = "user@example.com")
        String email,

        @NotBlank(message = ValidationMessages.PASSWORD_NOT_BLANK)
        @Size(min = 8, message = ValidationMessages.PASSWORD_MIN_SIZE)
        @Schema(description = "User's password", example = "securePassword123")
        String password
) {}