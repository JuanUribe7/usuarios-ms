package com.users.users_ms.domain.ports.in;

import com.users.users_ms.application.dto.response.LoginResponseDto;
import com.users.users_ms.domain.model.LoginResponse;

public interface LoginServicePort {
    LoginResponse login(String email, String password);
}
