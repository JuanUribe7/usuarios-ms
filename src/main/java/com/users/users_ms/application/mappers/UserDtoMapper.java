package com.users.users_ms.application.mappers;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.domain.model.User;

public class UserDtoMapper {

    public static User toModel(UserRequestDto dto) {
        return new User(null, dto.getName(), dto.getEmail());
    }

    public static UserResponseDto toDto(User model) {
        return new UserResponseDto(model.getId(), model.getName(), model.getEmail());
    }
}
