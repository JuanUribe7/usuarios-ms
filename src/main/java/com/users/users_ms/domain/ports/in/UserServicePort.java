package com.users.users_ms.domain.ports.in;

import com.users.users_ms.domain.model.User;

import java.util.Optional;

public interface UserServicePort {

    User saveOwner(User user);
    Optional<User> findById(Long id);


}
