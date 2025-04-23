package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.UserServicePort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;

public class UserUseCase implements UserServicePort {

    private final UserPersistencePort userPersistencePort;



    public UserUseCase(UserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;


    }


    @Override
    public User saveOwner(User user) {
        user.setRole(Role.OWNER);
        return userPersistencePort.saveUser(user);
    }
}
