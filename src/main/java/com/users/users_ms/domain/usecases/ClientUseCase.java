package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.IClientServicePort;
import com.users.users_ms.domain.ports.out.IUserPersistencePort;
import com.users.users_ms.domain.validation.Validator;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ClientUseCase implements IClientServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final PasswordEncoder passwordEncoder;

    public ClientUseCase(IUserPersistencePort userPersistencePort, PasswordEncoder passwordEncoder) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveClient(User client) {
        Validator.validate(client, userPersistencePort);
        client.setRole(Role.CLIENT);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return userPersistencePort.saveUser(client);
    }
}