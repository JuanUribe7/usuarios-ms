package com.users.users_ms.domain.ports.out;

import com.users.users_ms.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPersistencePort {
    List<User> getAllUsers();


    User update(User user);

    void delete(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    User findById(Long id);

    boolean existsByEmail(String email);
}
