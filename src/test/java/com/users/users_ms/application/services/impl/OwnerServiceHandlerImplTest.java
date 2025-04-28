// src/test/java/com/users/users_ms/application/services/impl/OwnerServiceHandlerImplTest.java

package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.IOwnerServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OwnerServiceHandlerImplTest {

    private IOwnerServicePort IOwnerServicePort;
    private UserDtoMapper mapper;
    private OwnerServiceHandlerImpl userService;

    @BeforeEach
    void setUp() {
        IOwnerServicePort = mock(IOwnerServicePort.class);
        mapper = mock(UserDtoMapper.class);
        userService = new OwnerServiceHandlerImpl(IOwnerServicePort, mapper);
    }

    @Test
    void saveOwner_ShouldReturnResponseDto() {
        UserRequestDto requestDto = new UserRequestDto("Juan", "Pérez", "12345678", "+573005698325", LocalDate.of(1990, 1, 1), "juan@example.com", "ClaveSegura123!");
        User user = new User();
        User savedUser = new User();
        UserResponseDto responseDto = new UserResponseDto();

        when(mapper.toModel(requestDto)).thenReturn(user);
        when(IOwnerServicePort.saveOwner(user)).thenReturn(savedUser);
        when(mapper.toResponseDto(savedUser)).thenReturn(responseDto);

        UserResponseDto result = userService.saveOwner(requestDto);

        assertNotNull(result);
        assertEquals(responseDto, result);
        verify(mapper).toModel(requestDto);
        verify(IOwnerServicePort).saveOwner(user);
        verify(mapper).toResponseDto(savedUser);
    }

    @Test
    void getUserRoleById_ShouldReturnRole() {
        User user = new User();
        user.setRole(Role.OWNER);

        when(IOwnerServicePort.findById(1L)).thenReturn(Optional.of(user));

        String role = userService.getUserRoleById(1L);

        assertEquals("OWNER", role);
        verify(IOwnerServicePort).findById(1L);
    }

    @Test
    void getUserRoleById_ShouldThrowException() {
        when(IOwnerServicePort.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getUserRoleById(1L));

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(IOwnerServicePort).findById(1L);
    }
}
