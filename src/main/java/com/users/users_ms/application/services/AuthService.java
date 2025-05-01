package com.users.users_ms.application.services;

import com.users.users_ms.application.dto.request.LoginRequestDto;
import com.users.users_ms.application.dto.response.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto dto);
}

