// src/test/java/com/users/users_ms/application/services/impl/UserServiceImplTest.java

package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.UserServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserServicePort userServicePort;
    private UserDtoMapper mapper;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userServicePort = mock(UserServicePort.class);
        mapper = mock(UserDtoMapper.class);
        userService = new UserServiceImpl(userServicePort, mapper);
    }

    @Test
    void saveOwner_ShouldReturnResponseDto() {
        UserRequestDto requestDto = new UserRequestDto("Juan", "Pérez", "12345678", "+573005698325", LocalDate.of(1990, 1, 1), "juan@example.com", "ClaveSegura123!");
        User user = new User();
        User savedUser = new User();
        UserResponseDto responseDto = new UserResponseDto();

        when(mapper.toModel(requestDto)).thenReturn(user);
        when(userServicePort.saveOwner(user)).thenReturn(savedUser);
        when(mapper.toResponseDto(savedUser)).thenReturn(responseDto);

        UserResponseDto result = userService.saveOwner(requestDto);

        assertNotNull(result);
        assertEquals(responseDto, result);
        verify(mapper).toModel(requestDto);
        verify(userServicePort).saveOwner(user);
        verify(mapper).toResponseDto(savedUser);
    }

    @Test
    void getUserRoleById_ShouldReturnRole() {
        User user = new User();
        user.setRole(Role.OWNER);

        when(userServicePort.findById(1L)).thenReturn(Optional.of(user));

        String role = userService.getUserRoleById(1L);

        assertEquals("OWNER", role);
        verify(userServicePort).findById(1L);
    }

    @Test
    void getUserRoleById_ShouldThrowException() {
        when(userServicePort.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getUserRoleById(1L));

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userServicePort).findById(1L);
    }
}
