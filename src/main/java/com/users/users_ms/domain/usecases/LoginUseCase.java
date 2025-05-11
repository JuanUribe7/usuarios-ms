package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.LoginResponse;
import com.users.users_ms.domain.ports.in.LoginServicePort;
import com.users.users_ms.domain.ports.out.AuthenticationPort;
import com.users.users_ms.domain.services.AuthenticationService;

public class LoginUseCase implements LoginServicePort {

    private final AuthenticationPort authenticationPort;
    private final AuthenticationService authenticationService;

    public LoginUseCase(AuthenticationPort authenticationPort, AuthenticationService authenticationService) {
        this.authenticationPort = authenticationPort;
        this.authenticationService = authenticationService;
    }

    @Override
    public LoginResponse login(String email, String password) {
        LoginResponse response = authenticationPort.authenticate(email, password);
        authenticationService.validateRole(response.getRole());
        return response;
    }
}