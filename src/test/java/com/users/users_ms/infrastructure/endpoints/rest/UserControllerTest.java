package com.users.users_ms.infrastructure.endpoints.rest;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.OwnerService;
import com.users.users_ms.application.services.ClientService;
import com.users.users_ms.infrastructure.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private OwnerService ownerService;
    private EmployeeService employeeService;
    private ClientService clientService;
    private UserDtoMapper mapper;
    private JwtUtil jwtUtil;
    private UserController userController;

    @BeforeEach
    void setUp() {
        ownerService = mock(OwnerService.class);
        employeeService = mock(EmployeeService.class);
        clientService = mock(ClientService.class);
        mapper                = mock(UserDtoMapper.class);
        jwtUtil               = mock(JwtUtil.class);

        userController = new UserController(
                ownerService,
                employeeService,
                clientService,
                mapper,
                jwtUtil
        );
    }

    @Test
    void createOwner_ShouldReturnUserResponseDto() {
        UserRequestDto requestDto = new UserRequestDto();
        UserResponseDto responseDto = new UserResponseDto();

        when(ownerService.saveOwner(requestDto)).thenReturn(responseDto);

        ResponseEntity<UserResponseDto> response = userController.createOwner(requestDto);

        assertEquals(responseDto, response.getBody());
        verify(ownerService).saveOwner(requestDto);
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
        when(employeeService.saveEmployee(requestDto, extractedOwnerId))
                .thenReturn(responseDto);

        ResponseEntity<UserResponseDto> response = userController.createEmployee(requestDto, mockRequest);

        assertEquals(responseDto, response.getBody());
        verify(jwtUtil).extractUserId(token);
        verify(employeeService).saveEmployee(requestDto, extractedOwnerId);
    }

    @Test
    void createClient_ShouldReturnUserResponseDto() {
        UserRequestDto requestDto = new UserRequestDto();
        UserResponseDto responseDto = new UserResponseDto();

        when(clientService.saveClient(requestDto)).thenReturn(responseDto);

        ResponseEntity<UserResponseDto> response = userController.createClient(requestDto);

        assertEquals(responseDto, response.getBody());
        verify(clientService).saveClient(requestDto);
    }

    @Test
    void updateOwnerRestaurant_ShouldCallServiceHandler() {
        Long ownerId = 1L;
        Long restaurantId = 2L;

        ResponseEntity<String> response = userController.updateOwnerRestaurant(ownerId, restaurantId);

        assertEquals("Restaurante asignado correctamente al propietario.", response.getBody());
        verify(ownerService).updateOwnerRestaurantId(ownerId, restaurantId);
    }

    @Test
    void getUserRoleById_ShouldReturnCorrectRole() {
        Long userId = 1L;
        String role = "OWNER";

        when(ownerService.getUserRoleById(userId)).thenReturn(role);

        ResponseEntity<String> response = userController.getUserRoleById(userId);

        assertEquals(role, response.getBody());
        verify(ownerService).getUserRoleById(userId);
    }
}
