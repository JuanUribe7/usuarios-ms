package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.ports.in.DeleteUserServicePort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteUserUseCase implements DeleteUserServicePort {

    private final UserPersistencePort userPort;

    @Override
    public void delete(Long id) {
        userPort.delete(id);
    }
}
