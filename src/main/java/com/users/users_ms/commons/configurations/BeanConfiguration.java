package com.users.users_ms.commons.configurations;

import com.users.users_ms.domain.ports.in.RegisterOwnerServicePort;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.usecases.RegisterOwnerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public RegisterOwnerServicePort registerOwnerUseCase(UserPersistencePort persistencePort,
                                                         PasswordEncoderPort passwordEncoderPort) {
        return new RegisterOwnerUseCase(persistencePort, passwordEncoderPort);
    }
}
