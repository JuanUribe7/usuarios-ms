package com.users.users_ms.commons.configurations.beans;



import com.users.users_ms.domain.ports.in.*;
import com.users.users_ms.domain.ports.out.*;
import com.users.users_ms.domain.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfiguration {


    @Bean
    public ListUserServicePort listUserService(UserPersistencePort userPersistencePort) {
        return new GetAllUsersUseCase(userPersistencePort);
    }

    @Bean
    public CreateUserServicePort registerUserUseCase(UserPersistencePort persistencePort) {
        return new CreateUserUseCase(persistencePort);
    }

    @Bean
    public GetUserServicePort getUserServicePort(UserPersistencePort persistencePort) {
        return new GetUserUseCase(persistencePort);
    }

    @Bean
    public UpdateUserServicePort updateUserServicePort(UserPersistencePort persistencePort) {
        return new UpdateUserUseCase( persistencePort);
    }
    @Bean
    public DeleteUserServicePort deleteUserServicePort(UserPersistencePort persistencePort) {
        return new DeleteUserUseCase(persistencePort);
    }

}
