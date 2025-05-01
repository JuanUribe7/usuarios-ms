package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.ports.in.ValidateServicePort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;

public class ValidateUseCase implements ValidateServicePort {

    private final UserPersistencePort userPort;

    public ValidateUseCase(UserPersistencePort userPort) {
        this.userPort = userPort;
    }

    @Override
    public boolean existsAndIsOwner(Long id) {
        return userPort.findById(id)
                .map(user -> user.getRole().equals(Role.OWNER))
                .orElse(false);
    }

}
