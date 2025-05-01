package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterEmployeeServicePort;
import com.users.users_ms.domain.ports.out.RestaurantFeignPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;


public class RegisterEmployeeUseCase implements RegisterEmployeeServicePort {

    private final PasswordEncoderPort passwordEncoder;
    private final UserPersistencePort userPersistencePort;
    private final RestaurantFeignPort restaurantAssignment;

    public RegisterEmployeeUseCase(PasswordEncoderPort passwordEncoder, RestaurantFeignPort restaurantAssignment, UserPersistencePort UserPersistencePort) {
        this.passwordEncoder = passwordEncoder;
        this .restaurantAssignment = restaurantAssignment;
        this.userPersistencePort = UserPersistencePort;
    }


    @Override
    public User saveEmployee(User employee, Long restaurantId) {
        User newEmployee=employee.createEmployee(userPersistencePort);
        String encodedPassword = passwordEncoder.encodePassword(newEmployee.getPassword());
        User userSaved=userPersistencePort.saveUser(newEmployee.withEncodedPassword(encodedPassword));
        restaurantAssignment.assignEmployeeToRestaurant(restaurantId, userSaved.getId());
        return userSaved;
    }
}
