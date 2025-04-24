package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.UserServicePort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.validation.PassValidator;
import com.users.users_ms.domain.validation.Validator;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserUseCase implements UserServicePort {

    private final PasswordEncoder passwordEncoder;
    private final UserPersistencePort userPersistencePort;

    public UserUseCase(UserPersistencePort userPersistencePort, PasswordEncoder passwordEncoder) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveOwner(User owner) {
        Validator.validate(owner, userPersistencePort);
        owner.setRole(Role.OWNER);
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        return userPersistencePort.saveUser(owner);
    }
}
