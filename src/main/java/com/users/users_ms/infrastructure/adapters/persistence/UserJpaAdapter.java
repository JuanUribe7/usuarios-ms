package com.users.users_ms.infrastructure.adapters.persistence;



import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.infrastructure.entities.UserEntity;
import com.users.users_ms.infrastructure.mappers.UserEntityMapper;
import com.users.users_ms.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserJpaAdapter implements UserPersistencePort {

    private final UserRepository userRepository;

    public UserJpaAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public User saveUser(User user) {

        UserEntity entity = UserEntityMapper.toEntity(user);
        UserEntity saved = userRepository.save(entity);
        return UserEntityMapper.toModel(saved);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(UserEntityMapper.toEntity(user));
    }

    @Override
    public  Optional<User> findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .map(UserEntityMapper::toModel);
    }
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    @Override
    public  Optional<User> findById(Long id) {
        return userRepository
                .findById(id)
                .map(UserEntityMapper::toModel);
    }


}
