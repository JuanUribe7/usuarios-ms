package com.users.users_ms.domain.usecases;

import com.users.users_ms.commons.constants.ValidationMessages;
import com.users.users_ms.domain.helper.UniquenessValidator;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterOwnerServicePort;
import com.users.users_ms.domain.ports.in.ValidationFields;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.services.UserValidationService;

public class RegisterOwnerUseCase implements RegisterOwnerServicePort {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final ValidationFields userValidationService;

    public RegisterOwnerUseCase(UserPersistencePort userPersistencePort,
                                PasswordEncoderPort passwordEncoderPort,
                                ValidationFields userValidationService) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.userValidationService = userValidationService;
    }

    @Override
    public User execute(User owner) {
        userValidationService.validateUser(owner);
        if (!owner.isAdult()) {
            throw new IllegalArgumentException(ValidationMessages.USER_NOT_ADULT);
        }
        UniquenessValidator.validate(() -> userPersistencePort.existsByEmail(owner.getEmail()), "email", owner.getEmail());
        User newOwner = owner.asRole(Role.OWNER);
        String encodedPassword = passwordEncoderPort.encodePassword(newOwner.getPassword());
        return userPersistencePort.saveUser(newOwner.withEncodedPassword(encodedPassword));
    }

}