package com.users.users_ms.domain.ports.out;

import com.users.users_ms.domain.model.User;
import java.util.Optional;

public interface UserPersistencePort {
    User saveUser(User user);
    void updateUser(User user);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findById(Long id);
}
