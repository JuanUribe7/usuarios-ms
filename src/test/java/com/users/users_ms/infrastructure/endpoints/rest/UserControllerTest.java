package com.users.users_ms.infrastructure.endpoints.rest;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.EmployeeServiceHandler;
import com.users.users_ms.application.services.OwnerServiceHandler;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.infrastructure.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private OwnerServiceHandler ownerServiceHandler;
    private EmployeeServiceHandler employeeServiceHandler;
    private UserDtoMapper mapper;
    private JwtUtil jwtUtil;
    private UserController userController;

    @BeforeEach
    void setUp() {
        ownerServiceHandler = mock(OwnerServiceHandler.class);
        employeeServiceHandler = mock(EmployeeServiceHandler.class);
        mapper = mock(UserDtoMapper.class);
        jwtUtil = mock(JwtUtil.class);
        userController = new UserController(ownerServiceHandler, employeeServiceHandler, mapper, jwtUtil);
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
        when(employeeServiceHandler.saveEmployee(requestDto, extractedOwnerId)).thenReturn(responseDto);

        ResponseEntity<UserResponseDto> response = userController.createEmployee(requestDto, mockRequest);

        assertEquals(responseDto, response.getBody());
        verify(jwtUtil).extractUserId(token);
        verify(employeeServiceHandler).saveEmployee(requestDto, extractedOwnerId);
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