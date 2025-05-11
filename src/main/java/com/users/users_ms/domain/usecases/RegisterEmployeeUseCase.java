package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.helper.UniquenessValidator;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterEmployeeServicePort;
import com.users.users_ms.domain.ports.out.RestaurantFeignPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;


public class RegisterEmployeeUseCase implements RegisterEmployeeServicePort {

    private final PasswordEncoderPort passwordEncoder;
    private final UserPersistencePort userPort;
    private final RestaurantFeignPort restaurantAssignment;

    public RegisterEmployeeUseCase(PasswordEncoderPort passwordEncoder,
                                   RestaurantFeignPort restaurantAssignment,
                                   UserPersistencePort userPort) {
        this.passwordEncoder = passwordEncoder;
        this .restaurantAssignment = restaurantAssignment;
        this.userPort = userPort;
    }


    @Override
    public void saveEmployee(User employee, Long restaurantId) {
        UniquenessValidator.validate(() -> userPort.existsByEmail(employee.getEmail()), "email", employee.getEmail());
        restaurantAssignment.validateRestaurantExists(restaurantId);
        User newEmployee = employee.asRole(Role.EMPLOYEE);
        String encodedPassword = passwordEncoder.encodePassword(newEmployee.getPassword());
        User userSaved = userPort.saveUser(newEmployee.withEncodedPassword(encodedPassword));
        restaurantAssignment.assignEmployeeToRestaurant(restaurantId, userSaved.getId());

    }
}
