package com.users.users_ms.infrastructure.endpoints.rest;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.services.OwnerService;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.constants.RoleConstants;
import com.users.users_ms.commons.exceptions.UnauthorizedAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private OwnerService ownerService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        ownerService = mock(OwnerService.class);
        userController = new UserController(ownerService);
    }

    @Test
    void createOwner_WithValidRole_ReturnsUserResponseDto() {

        UserRequestDto requestDto = new UserRequestDto();
        UserResponseDto responseDto = new UserResponseDto();
        when(ownerService.saveOwner(requestDto)).thenReturn(responseDto);

        ResponseEntity<UserResponseDto> response = userController.createOwner(requestDto, RoleConstants.ADMIN);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
        verify(ownerService, times(1)).saveOwner(requestDto);
    }

    @Test
    void createOwner_WithInvalidRole_ThrowsException() {
        // Arrange
        UserRequestDto requestDto = new UserRequestDto();

        // Act & Assert
        UnauthorizedAccessException thrown = assertThrows(
                UnauthorizedAccessException.class,
                () -> userController.createOwner(requestDto, "CLIENT")
        );
        assertEquals(ExceptionMessages.INVALID_ROLE_ADMIN, thrown.getMessage());
        verify(ownerService, never()).saveOwner(any());
    }
}
