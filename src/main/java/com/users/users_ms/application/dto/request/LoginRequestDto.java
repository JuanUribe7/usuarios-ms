package com.users.users_ms.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(
        @NotNull String email,
        String password
) {}