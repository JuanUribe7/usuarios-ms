package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.helper.UniquenessValidator;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterEmployeeServicePort;
import com.users.users_ms.domain.ports.in.ValidationFields;
import com.users.users_ms.domain.ports.out.RestaurantFeignPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;
import com.users.users_ms.domain.services.UserValidationService;


public class RegisterEmployeeUseCase implements RegisterEmployeeServicePort {

    private final PasswordEncoderPort passwordEncoder;
    private final UserPersistencePort userPort;
    private final RestaurantFeignPort restaurantAssignment;
    private final ValidationFields userValidationService;

    public RegisterEmployeeUseCase(PasswordEncoderPort passwordEncoder,
                                   RestaurantFeignPort restaurantAssignment,
                                   UserPersistencePort userPort,
                                   ValidationFields userValidationService) {
        this.passwordEncoder = passwordEncoder;
        this .restaurantAssignment = restaurantAssignment;
        this.userPort = userPort;
        this.userValidationService = userValidationService;
    }


    @Override
    public void saveEmployee(User employee, Long restaurantId) {
        userValidationService.validateUser(employee);
        UniquenessValidator.validate(() -> userPort.existsByEmail(employee.getEmail()), "email", employee.getEmail());
        restaurantAssignment.validateRestaurantExists(restaurantId);
        User newEmployee = employee.asRole(Role.EMPLOYEE);
        String encodedPassword = passwordEncoder.encodePassword(newEmployee.getPassword());
        User userSaved = userPort.saveUser(newEmployee.withEncodedPassword(encodedPassword));
        restaurantAssignment.assignEmployeeToRestaurant(restaurantId, userSaved.getId());

    }
}
