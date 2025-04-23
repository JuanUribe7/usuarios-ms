package com.users.users_ms.infrastructure.mappers;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.infrastructure.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    @Mapping(target = "password", source = "password")
    @Mapping(target = "id",        source = "id")
    @Mapping(target = "name",      source = "name")
    @Mapping(target = "lastName",  source = "lastName")
    @Mapping(target = "identityDocument", source = "identityDocument")
    @Mapping(target = "phone",     source = "phone")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "email",     source = "email")
    @Mapping(target = "role",      source = "role")
    User toModel(UserEntity entity);

    UserEntity toEntity(User model);

    List<User> toModelList(List<UserEntity> entities);

}
