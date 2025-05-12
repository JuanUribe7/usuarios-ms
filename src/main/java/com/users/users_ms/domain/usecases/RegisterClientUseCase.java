package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.helper.UniquenessValidator;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterClientServicePort;
import com.users.users_ms.domain.ports.in.ValidationFields;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;
import com.users.users_ms.domain.services.UserValidationService;

public class RegisterClientUseCase implements RegisterClientServicePort {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoder;
    private final ValidationFields userValidationService;

    public RegisterClientUseCase(UserPersistencePort userPersistencePort,
                                 PasswordEncoderPort passwordEncoder,
                                 ValidationFields userValidationService) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
        this.userValidationService = userValidationService;
    }

    @Override
    public User saveClient(User client) {
        userValidationService.validateUser(client);
        UniquenessValidator.validate(() -> userPersistencePort.existsByEmail(client.getEmail()), "email", client.getEmail());
        User newClient = client.asRole(Role.CLIENT);
        String encodedPassword = passwordEncoder.encodePassword(newClient.getPassword());
        return userPersistencePort.saveUser(client.withEncodedPassword(encodedPassword));
    }
}