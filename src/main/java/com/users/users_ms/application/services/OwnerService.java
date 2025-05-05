package com.users.users_ms.application.services;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;


public interface OwnerService {
    UserResponseDto saveOwner(UserRequestDto dto);

}
