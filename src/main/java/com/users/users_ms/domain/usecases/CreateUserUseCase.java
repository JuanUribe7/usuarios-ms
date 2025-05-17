package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.CreateUserServicePort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCase implements CreateUserServicePort {

    private final UserPersistencePort userPort;

    @Override
    public User create(User user) {
        return userPort.save(user);
    }
}
