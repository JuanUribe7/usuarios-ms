package com.users.users_ms.application.dto.response;

import com.users.users_ms.domain.model.Role;

public record UserResponseDto(
        Long id,
        String name,
        String lastName,
        String identityDocument,
        String email,
        Role role
) {}