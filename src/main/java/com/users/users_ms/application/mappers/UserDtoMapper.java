package com.users.users_ms.application.mappers;

import com.users.users_ms.application.dto.request.CreateEmployeeRequestDto;
import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.domain.model.User;

public class UserDtoMapper {

    private UserDtoMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static User toModel(UserRequestDto dto) {
        return new User(
                null,
                dto.getName(),
                dto.getLastName(),
                dto.getIdentityDocument(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getPhone(),
                dto.getBirthDate(),
                null
        );
    }
    public static User toModel(CreateEmployeeRequestDto dto) {
        return new User(
                null,
                dto.getName(),
                dto.getLastName(),
                dto.getIdentityDocument(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getPhone(),
                dto.getBirthDate(),
                null
        );
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getIdentityDocument(),
                user.getEmail(),
                user.getRole()
        );
    }
}
