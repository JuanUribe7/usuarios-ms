package com.users.users_ms.application.mappers;


import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    @Mapping(target="id", ignore=true)
    @Mapping(target = "role", ignore = true)
    User toModel(UserRequestDto dto);
    UserResponseDto toResponseDto(User model);
}