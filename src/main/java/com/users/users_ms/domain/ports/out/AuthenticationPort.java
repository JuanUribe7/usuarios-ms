package com.users.users_ms.domain.ports.out;

import com.users.users_ms.application.dto.response.LoginResponseDto;

public interface AuthenticationPort {
    LoginResponseDto authenticate(String email, String password);
}
