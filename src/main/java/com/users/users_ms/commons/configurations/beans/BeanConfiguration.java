package com.users.users_ms.commons.configurations.beans;


import com.users.users_ms.domain.ports.in.UserServicePort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.usecases.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class BeanConfiguration {

    @Bean(name = "ownerServicePort")
    public UserServicePort ownerServicePort(UserPersistencePort userPersistencePort) {
        return new UserUseCase(userPersistencePort, customPasswordEncoder());

    }

    @Bean
    public PasswordEncoder customPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
