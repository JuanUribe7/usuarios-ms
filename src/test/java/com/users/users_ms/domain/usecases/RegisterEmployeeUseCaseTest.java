package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;
import com.users.users_ms.domain.ports.out.RestaurantFeignPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RegisterEmployeeUseCaseTest {

    private PasswordEncoderPort encoderPort;
    private UserPersistencePort userPort;
    private RestaurantFeignPort restaurantFeign;
    private RegisterEmployeeUseCase useCase;

    @BeforeEach
    void setUp() {
        encoderPort = mock(PasswordEncoderPort.class);
        userPort = mock(UserPersistencePort.class);
        restaurantFeign = mock(RestaurantFeignPort.class);
        useCase = new RegisterEmployeeUseCase(encoderPort, restaurantFeign, userPort);
    }

    @Test
    void saveEmployee_shouldCreateEncodeAndAssignEmployee() {
        // Arrange
        User input = mock(User.class);
        User created = mock(User.class);
        User withEncoded = mock(User.class);
        User saved = mock(User.class);

        when(input.createEmployee(userPort)).thenReturn(created);
        when(created.getPassword()).thenReturn("abc123");
        when(encoderPort.encodePassword("abc123")).thenReturn("encodedPass");
        when(created.withEncodedPassword("encodedPass")).thenReturn(withEncoded);
        when(userPort.saveUser(withEncoded)).thenReturn(saved);
        when(saved.getId()).thenReturn(777L);

        // Act
        User result = useCase.saveEmployee(input, 999L);

        // Assert
        assertEquals(saved, result);
        verify(input).createEmployee(userPort);
        verify(encoderPort).encodePassword("abc123");
        verify(created).withEncodedPassword("encodedPass");
        verify(userPort).saveUser(withEncoded);
        verify(restaurantFeign).assignEmployeeToRestaurant(999L, 777L);
    }
}
