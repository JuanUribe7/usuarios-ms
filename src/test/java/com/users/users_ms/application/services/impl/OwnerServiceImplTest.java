// src/test/java/com/users/users_ms/application/services/impl/OwnerServiceImplTest.java

package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterOwnerServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OwnerServiceImplTest {

    private RegisterOwnerServicePort RegisterOwnerServicePort;
    private UserDtoMapper mapper;
    private OwnerServiceImpl userService;

    @BeforeEach
    void setUp() {
        RegisterOwnerServicePort = mock(RegisterOwnerServicePort.class);
        mapper = mock(UserDtoMapper.class);
        userService = new OwnerServiceImpl(RegisterOwnerServicePort, mapper);
    }

    @Test
    void saveOwner_ShouldReturnResponseDto() {
        UserRequestDto requestDto = new UserRequestDto("Juan", "Pérez", "12345678", "+573005698325", LocalDate.of(1990, 1, 1), "juan@example.com", "ClaveSegura123!");
        User user = new User();
        User savedUser = new User();
        UserResponseDto responseDto = new UserResponseDto();

        when(mapper.toModel(requestDto)).thenReturn(user);
        when(RegisterOwnerServicePort.saveOwner(user)).thenReturn(savedUser);
        when(mapper.toResponseDto(savedUser)).thenReturn(responseDto);

        UserResponseDto result = userService.saveOwner(requestDto);

        assertNotNull(result);
        assertEquals(responseDto, result);
        verify(mapper).toModel(requestDto);
        verify(RegisterOwnerServicePort).saveOwner(user);
        verify(mapper).toResponseDto(savedUser);
    }

    @Test
    void getUserRoleById_ShouldReturnRole() {
        User user = new User();
        user.setRole(Role.OWNER);

        when(RegisterOwnerServicePort.findById(1L)).thenReturn(Optional.of(user));

        String role = userService.getUserRoleById(1L);

        assertEquals("OWNER", role);
        verify(RegisterOwnerServicePort).findById(1L);
    }

    @Test
    void getUserRoleById_ShouldThrowException() {
        when(RegisterOwnerServicePort.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getUserRoleById(1L));

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(RegisterOwnerServicePort).findById(1L);
    }
}
