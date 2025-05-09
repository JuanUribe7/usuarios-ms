package com.users.users_ms.domain.usecases;


import com.users.users_ms.domain.model.LoginResponse;
import com.users.users_ms.domain.ports.in.LoginServicePort;
import com.users.users_ms.domain.ports.out.AuthenticationPort;

public class LoginUseCase implements LoginServicePort {

    private final AuthenticationPort authenticationPort;

    public LoginUseCase(AuthenticationPort authenticationPort) {
        this.authenticationPort = authenticationPort;
    }

    @Override
    public LoginResponse login(String email, String password) {
        return authenticationPort.authenticate(email, password);
    }
}
