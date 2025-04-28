package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.IOwnerServicePort;
import com.users.users_ms.domain.ports.out.IUserPersistencePort;
import com.users.users_ms.domain.validation.Validator;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class OwnerUseCase implements IOwnerServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final PasswordEncoder passwordEncoder;

    public OwnerUseCase(IUserPersistencePort userPersistencePort, PasswordEncoder passwordEncoder) {
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

    @Override
    public Optional<User> findById(Long id) {
        return userPersistencePort.findById(id);
    }

    @Override
    public void updateOwnerRestaurantId(Long ownerId, Long restaurantId) {
        Optional<User> owner = userPersistencePort.findById(ownerId);
        if (owner.isEmpty() || !owner.get().getRole().equals(Role.OWNER)) {
            throw new IllegalArgumentException("Propietario no encontrado o no válido.");
        }
        User updated = owner.get();
        updated.setRestaurantId(restaurantId);
        userPersistencePort.updateUser(updated);
    }
}
