package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.LoginRequestDto;
import com.users.users_ms.application.dto.response.LoginResponseDto;
import com.users.users_ms.application.services.AuthService;

import com.users.users_ms.domain.ports.in.LoginServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final LoginServicePort loginServicePort;

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        return loginServicePort.login(dto.getEmail(), dto.getPassword());
    }
}