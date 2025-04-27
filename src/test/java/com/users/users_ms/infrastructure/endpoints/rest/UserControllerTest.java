package com.users.users_ms.infrastructure.endpoints.rest;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserDtoMapper mapper;

    @InjectMocks
    private UserController userController;

    @Test
    void createOwner_ReturnsUserResponse() {
        // Arrange
        UserRequestDto dto = mock(UserRequestDto.class);
        UserResponseDto expectedResponse = new UserResponseDto();
        when(userService.saveOwner(dto)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<UserResponseDto> response = userController.createOwner(dto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(expectedResponse, response.getBody());
        verify(userService, times(1)).saveOwner(dto);
    }

    @Test
    void getUserRoleById_ReturnsRole() {
        // Arrange
        Long userId = 42L;
        String expectedRole = "OWNER";
        when(userService.getUserRoleById(userId)).thenReturn(expectedRole);

        // Act
        ResponseEntity<String> response = userController.getUserRoleById(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedRole, response.getBody());
        verify(userService, times(1)).getUserRoleById(userId);
    }
}
