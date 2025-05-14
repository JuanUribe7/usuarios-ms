package com.users.users_ms.domain.ports.in;

import com.users.users_ms.domain.model.User;

import java.util.List;

public interface ListUserServicePort {
    List<User> getAllUsers();
}
