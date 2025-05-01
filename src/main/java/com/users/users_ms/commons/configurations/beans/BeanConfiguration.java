package com.users.users_ms.commons.configurations.beans;


import com.users.users_ms.domain.ports.in.*;
import com.users.users_ms.domain.ports.out.AuthenticationPort;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;
import com.users.users_ms.domain.ports.out.RestaurantFeignPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfiguration {
    @Bean
    public RegisterClientServicePort registerClientUseCase(UserPersistencePort persistencePort,
                                                           PasswordEncoderPort passwordEncoderPort) {
        return new RegisterClientUseCase(persistencePort, passwordEncoderPort);
    }

    @Bean
    public ValidateServicePort validateUseCase(UserPersistencePort persistencePort) {
        return new ValidateUseCase(persistencePort);
    }

    @Bean
    public RegisterOwnerServicePort registerOwnerUseCase(UserPersistencePort persistencePort,
                                                         PasswordEncoderPort passwordEncoderPort) {
        return new RegisterOwnerUseCase(persistencePort, passwordEncoderPort);
    }
    @Bean
    public RegisterEmployeeServicePort registerEmployeeUseCase(PasswordEncoderPort passwordEncoderPort,
                                                               RestaurantFeignPort restaurantFeignPort,
                                                               UserPersistencePort persistencePort) {
        return new RegisterEmployeeUseCase(passwordEncoderPort, restaurantFeignPort, persistencePort);
    }
    @Bean
    public LoginServicePort loginUseCase(AuthenticationPort authenticationPort){
        return new LoginUseCase(authenticationPort);
    }
}
