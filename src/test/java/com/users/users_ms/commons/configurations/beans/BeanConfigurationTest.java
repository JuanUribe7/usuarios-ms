// src/test/java/com/users/users_ms/commons/configurations/beans/BeanConfigurationTest.java

package com.users.users_ms.commons.configurations.beans;

import com.users.users_ms.domain.ports.in.UserServicePort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.usecases.UserUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BeanConfigurationTest {

    @Test
    void passwordEncoder_ShouldReturnBCryptPasswordEncoder() {
        BeanConfiguration config = new BeanConfiguration();
        PasswordEncoder encoder = config.passwordEncoder();

        assertNotNull(encoder);
        assertTrue(encoder.matches("password", encoder.encode("password")));
    }

    @Test
    void ownerServicePort_ShouldReturnUserUseCaseInstance() {
        BeanConfiguration config = new BeanConfiguration();
        UserPersistencePort userPersistencePort = mock(UserPersistencePort.class);

        UserServicePort servicePort = config.ownerServicePort(userPersistencePort);

        assertNotNull(servicePort);
        assertTrue(servicePort instanceof UserUseCase);
    }
}
