package com.users.users_ms.domain.usecases;

import com.users.users_ms.commons.constants.ValidationMessages;
import com.users.users_ms.domain.helper.UniquenessValidator;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterOwnerServicePort;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;

public class RegisterOwnerUseCase implements RegisterOwnerServicePort {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;

    public RegisterOwnerUseCase(UserPersistencePort userPersistencePort, PasswordEncoderPort passwordEncoderPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public User execute(User owner) {
        if (!owner.isAdult()) {
            throw new IllegalArgumentException(ValidationMessages.USER_NOT_ADULT);
        }
        UniquenessValidator.validate(() -> userPersistencePort.existsByEmail(owner.getEmail()), "email", owner.getEmail());
        User newOwner = owner.asRole(Role.OWNER);
        String encodedPassword = passwordEncoderPort.encodePassword(newOwner.getPassword());
        return userPersistencePort.saveUser(newOwner.withEncodedPassword(encodedPassword));
    }

}