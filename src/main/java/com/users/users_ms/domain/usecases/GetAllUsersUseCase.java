package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.ListUserServicePort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;

import java.util.List;

public class GetAllUsersUseCase implements ListUserServicePort {

    private final UserPersistencePort userPersistencePort;

    public GetAllUsersUseCase(UserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }
}
