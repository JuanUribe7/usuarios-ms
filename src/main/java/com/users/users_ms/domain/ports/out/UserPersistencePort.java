package com.users.users_ms.domain.ports.out;

import com.users.users_ms.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPersistencePort {
    User saveUser(User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    void updateUser(User user);

    Optional<User> findByEmail(String email);
    Optional<User> findByIdentityDocument(String identityDocument);
}
