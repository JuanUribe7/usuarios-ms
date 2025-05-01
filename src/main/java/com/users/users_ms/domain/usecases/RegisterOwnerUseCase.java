package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterOwnerServicePort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;

import java.util.Optional;

public class RegisterOwnerUseCase implements RegisterOwnerServicePort {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;

    public RegisterOwnerUseCase(UserPersistencePort userPersistencePort, PasswordEncoderPort passwordEncoderPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public User saveOwner(User owner) {
        User newOwner=owner.createOwner(userPersistencePort);
        String encodedPassword = passwordEncoderPort.encodePassword(newOwner.getPassword());
        return userPersistencePort.saveUser(newOwner.withEncodedPassword(encodedPassword));
    }




}
