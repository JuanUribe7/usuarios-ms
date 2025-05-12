package com.users.users_ms.commons.configurations.beans;


import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.*;
import com.users.users_ms.domain.ports.out.*;
import com.users.users_ms.domain.services.AuthenticationService;
import com.users.users_ms.domain.services.UserValidationService;
import com.users.users_ms.domain.services.ValidationFieldsService;
import com.users.users_ms.domain.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfiguration {

    @Bean
    public ValidationFields validationFields() {
        return new UserValidationService(new ValidationFieldsService());
    }

    @Bean
    public RegisterClientServicePort registerClientUseCase(UserPersistencePort persistencePort,
                                                           PasswordEncoderPort passwordEncoderPort,
                                                           ValidationFields validationFields) {
        return new RegisterClientUseCase(persistencePort, passwordEncoderPort, validationFields);
    }

    @Bean
    public ValidateServicePort validateUseCase(UserPersistencePort persistencePort) {
        return new ValidateOwnerUseCase(persistencePort);
    }

    @Bean
    public RegisterOwnerServicePort registerOwnerUseCase(UserPersistencePort persistencePort,
                                                         PasswordEncoderPort passwordEncoderPort,
                                                         ValidationFields validationFields) {
        return new RegisterOwnerUseCase(persistencePort, passwordEncoderPort, validationFields);
    }
    @Bean
    public RegisterEmployeeServicePort registerEmployeeUseCase(PasswordEncoderPort passwordEncoderPort,
                                                               RestaurantFeignPort restaurantFeignPort,
                                                               UserPersistencePort persistencePort,
                                                               ValidationFields validationFields) {
        return new RegisterEmployeeUseCase(passwordEncoderPort, restaurantFeignPort, persistencePort,
                validationFields);
    }
    @Bean
    public LoginServicePort loginUseCase(AuthenticationPort authenticationPort, AuthenticationService authenticationService) {
        return new LoginUseCase(authenticationPort, authenticationService);
    }
    @Bean
    public AuthenticationService authenticationService(UserPersistencePort userPersistencePort,
                                                       TokenProviderPort tokenProviderPort) {
        return new AuthenticationService(userPersistencePort, tokenProviderPort);
    }
}
