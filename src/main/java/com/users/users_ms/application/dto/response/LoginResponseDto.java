package com.users.users_ms.application.dto.response;

public record LoginResponseDto(
        String token,
        String email,
        String role
) {}