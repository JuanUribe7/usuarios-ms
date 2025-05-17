package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.UserService;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final ListUserServicePort listUserServicePort;
    private final CreateUserServicePort createUserUseCase;
    private final GetUserServicePort getUserUseCase;
    private final UpdateUserServicePort updateUserUseCase;
    private final DeleteUserServicePort deleteUserUseCase;

    @Override
    public UserResponseDto save(UserRequestDto dto) {
        User model = UserDtoMapper.toModel(dto);
        User saved = createUserUseCase.create(model);
        return UserDtoMapper.toDto(saved);
    }

    @Override
    public UserResponseDto findById(Long id) {
        User found = getUserUseCase.getById(id);
        return UserDtoMapper.toDto(found);
    }

    @Override
    public UserResponseDto update(Long id, UserRequestDto dto) {
        User model = UserDtoMapper.toModel(dto);
        model.setId(id);
        User updated = updateUserUseCase.update(model);
        return UserDtoMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        deleteUserUseCase.delete(id);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return listUserServicePort.getAllUsers()
                .stream()
                .map(UserDtoMapper::toDto)
                .toList();
    }
}
