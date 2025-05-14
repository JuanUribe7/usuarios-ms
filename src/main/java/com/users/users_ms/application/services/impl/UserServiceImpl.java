package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.UserService;
import com.users.users_ms.domain.ports.in.ListUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final ListUserServicePort listUserServicePort;
    private final UserDtoMapper userDtoMapper;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return listUserServicePort.getAllUsers()
                .stream()
                .map(userDtoMapper::toDto)
                .toList();
    }
}
