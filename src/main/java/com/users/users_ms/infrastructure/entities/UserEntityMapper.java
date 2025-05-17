package com.users.users_ms.infrastructure.entities;

import com.users.users_ms.domain.model.User;

public class UserEntityMapper {

    private UserEntityMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static UserEntity toEntity(User model) {
        UserEntity entity = new UserEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setEmail(model.getEmail());
        return entity;
    }

    public static User toModel(UserEntity entity) {
        return new User(entity.getId(), entity.getName(), entity.getEmail());
    }
}
