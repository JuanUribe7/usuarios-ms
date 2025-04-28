package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.IEmployeeServicePort;
import com.users.users_ms.domain.ports.out.IRestaurantValidationPort;
import com.users.users_ms.domain.ports.out.IUserPersistencePort;
import com.users.users_ms.domain.validation.Validator;
import org.springframework.security.crypto.password.PasswordEncoder;


public class EmployeeUseCase implements IEmployeeServicePort {

    private final PasswordEncoder passwordEncoder;
    private final IUserPersistencePort IUserPersistencePort;
    private final IRestaurantValidationPort IRestaurantValidationPort;

    public EmployeeUseCase(PasswordEncoder passwordEncoder, IRestaurantValidationPort IRestaurantValidationPort, IUserPersistencePort IUserPersistencePort) {
        this.passwordEncoder = passwordEncoder;
        this.IRestaurantValidationPort = IRestaurantValidationPort;
        this.IUserPersistencePort = IUserPersistencePort;
    }


    @Override
    public User saveEmployee(User employee, Long ownerId) {
        boolean isOwner = IRestaurantValidationPort.isOwnerOfRestaurant(employee.getRestaurantId(), ownerId);
        if (!isOwner) {
            throw new IllegalArgumentException("No eres propietario de este restaurante.");
        }
        Validator.validate(employee, IUserPersistencePort);
        employee.setRole(Role.EMPLOYEE);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return IUserPersistencePort.saveUser(employee);
    }
}
