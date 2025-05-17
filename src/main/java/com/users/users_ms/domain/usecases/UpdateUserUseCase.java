package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.UpdateUserServicePort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateUserUseCase implements UpdateUserServicePort {

    private final UserPersistencePort userPort;

    @Override
    public User update(User user) {
        return userPort.update(user);
    }
}
