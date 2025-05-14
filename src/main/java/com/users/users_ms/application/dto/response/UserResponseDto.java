package com.users.users_ms.application.dto.response;

public record UserResponseDto(
    Long id,
    String name,
    String lastName,
    String email,
    String phone
) {}
