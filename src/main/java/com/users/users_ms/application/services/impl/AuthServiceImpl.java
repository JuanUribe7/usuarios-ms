package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.LoginRequestDto;
import com.users.users_ms.application.dto.response.LoginResponseDto;
import com.users.users_ms.application.services.AuthService;

import com.users.users_ms.domain.model.LoginResponse;
import com.users.users_ms.domain.ports.in.LoginServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final LoginServicePort loginServicePort;

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        LoginResponse response = loginServicePort.login(dto.email(), dto.password());
        return new LoginResponseDto(response.getToken(), response.getEmail(), response.getRole());
    }
}