
package com.users.users_ms.commons.configurations.beans;

import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.ports.in.UserServicePort;
import com.users.users_ms.domain.usecases.UserUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BeanConfiguration.class)
class BeanConfigurationTest {

    @Autowired
    private ApplicationContext ctx;

    @MockBean
    private UserPersistencePort userPersistencePort;

    @Test
    void ownerServicePortBean_shouldBeUserUseCase() {
        Object bean = ctx.getBean("ownerServicePort");
        assertThat(bean).isInstanceOf(UserServicePort.class)
                .isInstanceOf(UserUseCase.class);
    }

    @Test
    void customPasswordEncoderBean_shouldBeBCrypt() {
        PasswordEncoder encoder = ctx.getBean(PasswordEncoder.class);
        assertThat(encoder).isInstanceOf(BCryptPasswordEncoder.class);
    }
}