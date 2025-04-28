package com.users.users_ms.infrastructure.endpoints.rest;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.EmployeeServiceHandler;
import com.users.users_ms.application.services.OwnerServiceHandler;
import com.users.users_ms.application.services.impl.ClientServiceHandler;
import com.users.users_ms.infrastructure.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private OwnerServiceHandler ownerServiceHandler;
    private EmployeeServiceHandler employeeServiceHandler;
    private ClientServiceHandler clientServiceHandler;
    private UserDtoMapper mapper;
    private JwtUtil jwtUtil;
    private UserController userController;

    @BeforeEach
    void setUp() {
        ownerServiceHandler   = mock(OwnerServiceHandler.class);
        employeeServiceHandler = mock(EmployeeServiceHandler.class);
        clientServiceHandler  = mock(ClientServiceHandler.class);
        mapper                = mock(UserDtoMapper.class);
        jwtUtil               = mock(JwtUtil.class);

        userController = new UserController(
                ownerServiceHandler,
                employeeServiceHandler,
                clientServiceHandler,
                mapper,
                jwtUtil
        );
    }

    @Test
    void createOwner_ShouldReturnUserResponseDto() {
        UserRequestDto requestDto = new UserRequestDto();
        UserResponseDto responseDto = new UserResponseDto();

        when(ownerServiceHandler.saveOwner(requestDto)).thenReturn(responseDto);

        ResponseEntity<UserResponseDto> response = userController.createOwner(requestDto);

        assertEquals(responseDto, response.getBody());
        verify(ownerServiceHandler).saveOwner(requestDto);
    }

    @Test
    void createEmployee_ShouldExtractOwnerIdFromToken_AndReturnUserResponseDto() {
        UserRequestDto requestDto = new UserRequestDto();
        UserResponseDto responseDto = new UserResponseDto();
        String token = "fake-jwt-token";
        Long extractedOwnerId = 123L;

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUserId(token)).thenReturn(extractedOwnerId);
        when(employeeServiceHandler.saveEmployee(requestDto, extractedOwnerId))
                .thenReturn(responseDto);

        ResponseEntity<UserResponseDto> response = userController.createEmployee(requestDto, mockRequest);

        assertEquals(responseDto, response.getBody());
        verify(jwtUtil).extractUserId(token);
        verify(employeeServiceHandler).saveEmployee(requestDto, extractedOwnerId);
    }

    @Test
    void createClient_ShouldReturnUserResponseDto() {
        UserRequestDto requestDto = new UserRequestDto();
        UserResponseDto responseDto = new UserResponseDto();

        when(clientServiceHandler.saveClient(requestDto)).thenReturn(responseDto);

        ResponseEntity<UserResponseDto> response = userController.createClient(requestDto);

        assertEquals(responseDto, response.getBody());
        verify(clientServiceHandler).saveClient(requestDto);
    }

    @Test
    void updateOwnerRestaurant_ShouldCallServiceHandler() {
        Long ownerId = 1L;
        Long restaurantId = 2L;

        ResponseEntity<String> response = userController.updateOwnerRestaurant(ownerId, restaurantId);

        assertEquals("Restaurante asignado correctamente al propietario.", response.getBody());
        verify(ownerServiceHandler).updateOwnerRestaurantId(ownerId, restaurantId);
    }

    @Test
    void getUserRoleById_ShouldReturnCorrectRole() {
        Long userId = 1L;
        String role = "OWNER";

        when(ownerServiceHandler.getUserRoleById(userId)).thenReturn(role);

        ResponseEntity<String> response = userController.getUserRoleById(userId);

        assertEquals(role, response.getBody());
        verify(ownerServiceHandler).getUserRoleById(userId);
    }
}
