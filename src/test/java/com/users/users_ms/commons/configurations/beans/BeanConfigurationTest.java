
package com.users.users_ms.commons.configurations.beans;

import com.users.users_ms.domain.ports.in.RegisterEmployeeServicePort;
import com.users.users_ms.domain.ports.in.RegisterOwnerServicePort;
import com.users.users_ms.domain.ports.out.RestaurantFeignPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.usecases.RegisterEmployeeUseCase;
import com.users.users_ms.domain.usecases.RegisterOwnerUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BeanConfigurationTest {

    private BeanConfiguration config;

    @BeforeEach
    void setUp() {
        config = new BeanConfiguration();
    }

    @Test
    void shouldInstantiateOwnerUseCaseWithDependencies() throws Exception {
        UserPersistencePort userRepository = mock(UserPersistencePort.class);
        RegisterOwnerServicePort ownerServicePort = config.ownerServicePort(userRepository);

        assertNotNull(ownerServicePort, "El servicio de propietario no debería ser nulo");
        assertTrue(ownerServicePort instanceof RegisterOwnerUseCase, "Debe instanciar RegisterOwnerUseCase");

        Field repositoryField = RegisterOwnerUseCase.class.getDeclaredField("userPersistencePort");
        repositoryField.setAccessible(true);
        assertSame(userRepository, repositoryField.get(ownerServicePort), "El repositorio inyectado no coincide");

        Field encoderField = RegisterOwnerUseCase.class.getDeclaredField("passwordEncoder");
        encoderField.setAccessible(true);
        assertTrue(encoderField.get(ownerServicePort) instanceof BCryptPasswordEncoder, "Debe usar BCryptPasswordEncoder");
    }

    @Test
    void shouldInstantiateEmployeeUseCaseWithDependencies() throws Exception {
        RestaurantFeignPort validationPort = mock(RestaurantFeignPort.class);
        UserPersistencePort userRepository = mock(UserPersistencePort.class);
        RegisterEmployeeServicePort employeeServicePort = config.employeeServicePort(validationPort, userRepository);

        assertNotNull(employeeServicePort, "El servicio de empleado no debería ser nulo");
        assertTrue(employeeServicePort instanceof RegisterEmployeeUseCase, "Debe instanciar RegisterEmployeeUseCase");

        Class<?> clazz = employeeServicePort.getClass();
        Field validationField = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.getType().equals(RestaurantFeignPort.class))
                .findFirst()
                .orElseThrow(() -> new NoSuchFieldException("Campo de validación de restaurante no encontrado"));
        validationField.setAccessible(true);
        assertSame(validationPort, validationField.get(employeeServicePort), "El puerto de validación inyectado no coincide");

        Field repositoryField = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.getType().equals(UserPersistencePort.class))
                .findFirst()
                .orElseThrow(() -> new NoSuchFieldException("Campo de persistencia de usuario no encontrado"));
        repositoryField.setAccessible(true);
        assertSame(userRepository, repositoryField.get(employeeServicePort), "El puerto de persistencia inyectado no coincide");

        Field encoderField = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.getType().equals(PasswordEncoder.class))
                .findFirst()
                .orElseThrow(() -> new NoSuchFieldException("Campo de codificador de contraseña no encontrado"));
        encoderField.setAccessible(true);
        assertTrue(encoderField.get(employeeServicePort) instanceof BCryptPasswordEncoder, "Debe usar BCryptPasswordEncoder");
    }

    @Test
    void shouldReturnBCryptPasswordEncoderAndEncodeAndMatch() {
        PasswordEncoder encoder = config.customPasswordEncoder();

        assertNotNull(encoder, "El codificador no debería ser nulo");
        assertTrue(encoder instanceof BCryptPasswordEncoder, "Debe instanciar BCryptPasswordEncoder");

        String rawPassword = "mySecret123";
        String encoded = encoder.encode(rawPassword);
        assertTrue(encoder.matches(rawPassword, encoded), "La contraseña codificada debe coincidir con la original");
    }
}
