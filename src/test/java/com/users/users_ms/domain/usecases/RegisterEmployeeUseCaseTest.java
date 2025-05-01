package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.RestaurantFeignPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class RegisterEmployeeUseCaseTest {

    private PasswordEncoder passwordEncoder;
    private UserPersistencePort userPersistencePort;
    private RestaurantFeignPort restaurantFeignPort;
    private RegisterEmployeeUseCase registerEmployeeUseCase;

    @BeforeEach
    void setUp() {
        passwordEncoder = mock(PasswordEncoder.class);
        userPersistencePort = mock(UserPersistencePort.class);
        restaurantFeignPort = mock(RestaurantFeignPort.class);
        registerEmployeeUseCase = new RegisterEmployeeUseCase(passwordEncoder, restaurantFeignPort, userPersistencePort);
    }

    @Test
    void saveEmployee_ShouldSave_WhenOwnerIsValid() {
        User employee = new User();
        employee.setName("John");
        employee.setLastName("Doe");
        employee.setIdentityDocument("123456789");
        employee.setEmail("juribe0700@gmail.com");
        employee.setPhone("+573005698325");
        employee.setPassword("1042241877Ju@n");
        employee.setRestaurantId(1L);
        employee.setBirthDate(LocalDate.of(1995, 5, 10)); // Fecha válida

        when(restaurantFeignPort.isOwnerOfRestaurant(1L, 10L)).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userPersistencePort.saveUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = registerEmployeeUseCase.saveEmployee(employee, 10L);

        assertEquals(Role.EMPLOYEE, savedUser.getRole());
        assertEquals("encodedPassword", savedUser.getPassword());
        verify(userPersistencePort).saveUser(employee);
    }

    @Test
    void saveEmployee_ShouldThrow_WhenNotOwner() {
        User employee = new User();
        employee.setRestaurantId(2L);
        employee.setBirthDate(LocalDate.of(1995, 5, 10)); // Fecha válida

        when(restaurantFeignPort.isOwnerOfRestaurant(2L, 5L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                registerEmployeeUseCase.saveEmployee(employee, 5L));

        assertEquals("No eres propietario de este restaurante.", exception.getMessage());
        verify(userPersistencePort, never()).saveUser(any());
    }
}