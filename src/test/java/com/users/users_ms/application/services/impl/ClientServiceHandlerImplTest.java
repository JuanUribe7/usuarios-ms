package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.IClientServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientServiceHandlerImplTest {

    @Mock
    private IClientServicePort clientServicePort;

    @Mock
    private UserDtoMapper mapper;

    private ClientServiceHandlerImpl handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new ClientServiceHandlerImpl(clientServicePort, mapper);
    }

    @Test
    void shouldMapDtoToModelCallPortAndMapToResponse() {
        // Arrange
        UserRequestDto dto = new UserRequestDto();
        User model = new User();
        User savedModel = new User();
        UserResponseDto responseDto = new UserResponseDto();

        when(mapper.toModel(dto)).thenReturn(model);
        when(clientServicePort.saveClient(model)).thenReturn(savedModel);
        when(mapper.toResponseDto(savedModel)).thenReturn(responseDto);

        // Act
        UserResponseDto result = handler.saveClient(dto);

        // Assert
        assertSame(responseDto, result, "Should return exactly the response from mapper");
        verify(mapper).toModel(dto);
        verify(clientServicePort).saveClient(model);
        verify(mapper).toResponseDto(savedModel);
    }

    @Test
    void shouldPropagateExceptionIfPortThrows() {
        // Arrange
        UserRequestDto dto = new UserRequestDto();
        User model = new User();

        when(mapper.toModel(dto)).thenReturn(model);
        when(clientServicePort.saveClient(model))
                .thenThrow(new IllegalStateException("port failure"));

        // Act & Assert
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> handler.saveClient(dto)
        );
        assertEquals("port failure", ex.getMessage());
        verify(mapper).toModel(dto);
        verify(clientServicePort).saveClient(model);
        // mapper.toResponseDto(...) should not be called on error
        verify(mapper, never()).toResponseDto(any());
    }
}