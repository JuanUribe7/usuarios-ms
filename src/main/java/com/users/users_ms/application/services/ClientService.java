package com.users.users_ms.application.services;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;

public interface ClientService {
    UserResponseDto saveClient(UserRequestDto dto);

    String getPhoneByUserId(Long id);
}
