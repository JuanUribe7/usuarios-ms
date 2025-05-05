package com.users.users_ms.application.mappers;


import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.domain.model.User;

public class UserDtoMapper {

    private UserDtoMapper() {
        throw new IllegalStateException(ExceptionMessages.UTILITY_CLASS_INSTANTIATION);
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
