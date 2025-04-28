package com.users.users_ms.commons.configurations.beans;


import com.users.users_ms.domain.ports.in.IEmployeeServicePort;
import com.users.users_ms.domain.ports.in.IOwnerServicePort;
import com.users.users_ms.domain.ports.out.IRestaurantValidationPort;
import com.users.users_ms.domain.ports.out.IUserPersistencePort;
import com.users.users_ms.domain.usecases.EmployeeUseCase;
import com.users.users_ms.domain.usecases.OwnerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class BeanConfiguration {

    @Bean(name = "ownerServicePort")
    public IOwnerServicePort ownerServicePort(IUserPersistencePort userPersistencePort) {
        return new OwnerUseCase(userPersistencePort, customPasswordEncoder());
    }

    @Bean(name = "employeeServicePort")
    public IEmployeeServicePort employeeServicePort(IRestaurantValidationPort restaurantValidationPort,
                                                    IUserPersistencePort userPersistencePort) {
        return new EmployeeUseCase(customPasswordEncoder(), restaurantValidationPort, userPersistencePort);
    }

    @Bean
    public PasswordEncoder customPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
