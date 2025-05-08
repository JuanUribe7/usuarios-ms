package com.users.users_ms.domain.ports.in;

import com.users.users_ms.domain.model.User;

import java.util.Optional;

public interface RegisterOwnerServicePort {

    User execute(User user);

}
