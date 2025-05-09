package com.users.users_ms.infrastructure.mappers;


import com.users.users_ms.commons.constants.ExceptionMessages;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.infrastructure.entities.UserEntity;



public class UserEntityMapper {

    private UserEntityMapper() {
        throw new IllegalStateException(ExceptionMessages.UTILITY_CLASS_INSTANTIATION);
    }

    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setLastName(user.getLastName());
        entity.setIdentityDocument(user.getIdentityDocument());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setPhone(user.getPhone());
        entity.setBirthDate(user.getBirthDate());
        entity.setRole(user.getRole());


        return entity;
    }

    public static User toModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }



        return new User(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getIdentityDocument(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getPhone(),
                entity.getBirthDate(),
                entity.getRole()
        );
    }
}
