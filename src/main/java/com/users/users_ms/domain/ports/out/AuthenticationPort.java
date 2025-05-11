package com.users.users_ms.domain.ports.out;

import com.users.users_ms.domain.model.LoginResponse;

public interface AuthenticationPort {
    LoginResponse authenticate(String email, String password);
}
