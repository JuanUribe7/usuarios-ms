package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.GetUserServicePort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUserUseCase implements GetUserServicePort {

    private final UserPersistencePort userPort;

    @Override
    public User getById(Long id) {
        return userPort.findById(id);
    }
}
