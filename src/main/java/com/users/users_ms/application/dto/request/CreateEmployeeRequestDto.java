package com.users.users_ms.application.dto.request;

import java.time.LocalDate;

public record CreateEmployeeRequestDto(
        String name,
        String lastName,
        String identityDocument,
        String email,
        String password,
        String phone,
        LocalDate birthDate,
        Long restaurantId
) {}