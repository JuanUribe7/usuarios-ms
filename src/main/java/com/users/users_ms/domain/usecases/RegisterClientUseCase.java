package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterClientServicePort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;

public class RegisterClientUseCase implements RegisterClientServicePort {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoder;

    public RegisterClientUseCase(UserPersistencePort userPersistencePort,
                                 PasswordEncoderPort passwordEncoder) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveClient(User client) {
         client = client.createClient(userPersistencePort);
        String encodedPassword = passwordEncoder.encodePassword(client.getPassword());
        return userPersistencePort.saveUser(client.withEncodedPassword(encodedPassword));
    }
}