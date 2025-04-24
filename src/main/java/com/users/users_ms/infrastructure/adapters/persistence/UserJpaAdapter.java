package com.users.users_ms.infrastructure.adapters.persistence;


import com.users.users_ms.domain.exceptions.InvalidEmailException;
import com.users.users_ms.domain.exceptions.InvalidIdentityDocumentException;
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
    private final UserEntityMapper mapper;

    public UserJpaAdapter(UserRepository userRepository, UserEntityMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public User saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new InvalidEmailException("El correo ya está registrado.");
        }
        if (userRepository.findByIdentityDocument(user.getIdentityDocument()).isPresent()) {
            throw new InvalidIdentityDocumentException("El documento de identidad ya está registrado.");
        }

        UserEntity entity = mapper.toEntity(user);
        UserEntity saved = userRepository.save(entity);
        User result = mapper.toModel(saved);
        return result;
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(mapper.toEntity(user)); // JPA detecta que es un update si ya tiene ID
    }

    @Override
    public  Optional<User> findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .map(mapper::toModel);
    }

    @Override
    public List<User> findAll() {
        return mapper.toModelList(userRepository.findAll());
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(mapper::toModel);
    }

    @Override
    public Optional<User> findByIdentityDocument(String identityDocument) {
        return userRepository
                .findByIdentityDocument(identityDocument)
                .map(mapper::toModel);
    }
}
