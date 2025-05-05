package com.users.users_ms.infrastructure.adapters.persistence;


import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.infrastructure.entities.UserEntity;
import com.users.users_ms.infrastructure.mappers.UserEntityMapper;
import com.users.users_ms.infrastructure.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@RequiredArgsConstructor
@Repository
public class UserJpaAdapter implements UserPersistencePort {

    private final UserRepository userRepository;


    @Override
    public User saveUser(User user) {

        UserEntity entity = UserEntityMapper.toEntity(user);
        UserEntity saved = userRepository.save(entity);
        return UserEntityMapper.toModel(saved);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }




}
