package com.users.users_ms.application.mappers;

import com.users.users_ms.application.dto.request.*;

import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {


    @Mapping(target = "role", constant = "OWNER")
    User toModel(CreateOwnerRequestDto dto);

    @Mapping(target = "role", constant = "EMPLOYEE")
    User toModel(CreateEmployeeRequestDto dto);

    @Mapping(target = "role", constant = "CLIENT")
    User toModel(CreateClientRequestDto dto);

    UserResponseDto toDto(User user);

}