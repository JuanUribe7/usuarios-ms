package com.users.users_ms.domain.ports.in;

import com.users.users_ms.application.dto.response.LoginResponseDto;

public interface LoginServicePort {
    LoginResponseDto login(String email, String password);
}
