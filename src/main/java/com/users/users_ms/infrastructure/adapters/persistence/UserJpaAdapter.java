package com.users.users_ms.infrastructure.adapters.persistence;



import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.infrastructure.entities.UserEntity;
import com.users.users_ms.infrastructure.entities.UserEntityMapper;
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
    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserEntityMapper::toModel)
                .toList();
    }

    @Override
    public User save(User user) {
        UserEntity saved = userRepository.save(UserEntityMapper.toEntity(user));
        return UserEntityMapper.toModel(saved);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .map(UserEntityMapper::toModel)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User update(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException("User not found");
        }
        UserEntity updated = userRepository.save(UserEntityMapper.toEntity(user));
        return UserEntityMapper.toModel(updated);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
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



}
