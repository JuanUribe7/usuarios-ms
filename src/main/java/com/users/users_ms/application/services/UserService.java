package com.users.users_ms.application.services;

import com.users.users_ms.application.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();
}
