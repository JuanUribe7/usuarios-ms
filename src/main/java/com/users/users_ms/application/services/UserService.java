package com.users.users_ms.application.services;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto save(UserRequestDto dto);
    UserResponseDto findById(Long id);
    UserResponseDto update(Long id, UserRequestDto dto);
    void delete(Long id);
    List<UserResponseDto> getAllUsers();
}
