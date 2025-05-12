package com.users.users_ms.domain.ports.in;

import com.users.users_ms.domain.model.User;

public interface ValidationFields {
    void validateUser(User user);
}
