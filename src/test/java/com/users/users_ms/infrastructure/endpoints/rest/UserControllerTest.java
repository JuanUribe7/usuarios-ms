package com.users.users_ms.infrastructure.endpoints.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.users_ms.application.dto.request.CreateEmployeeRequestDto;
import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.services.ClientService;
import com.users.users_ms.application.services.EmployeeService;
import com.users.users_ms.application.services.OwnerService;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.infrastructure.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserResponseDto mockResponse;
    private final String FAKE_TOKEN = "fake-token";

    @BeforeEach
    void setup() {
        mockResponse = new UserResponseDto(
                1L,
                "Juan",
                "Uribe",
                "12345678",
                "juan@example.com",
                Role.OWNER
        );

        // Simular extracción de datos del token
        Mockito.when(jwtUtil.extractUserId(FAKE_TOKEN)).thenReturn(1L);
        Mockito.when(jwtUtil.extractUsername(FAKE_TOKEN)).thenReturn("juan@example.com"); // <— clave
        Mockito.when(jwtUtil.extractRole(FAKE_TOKEN)).thenReturn("OWNER");                 // default OWNER
    }

    @Test
    void createOwner_shouldReturnCreatedUser() throws Exception {
        // Para este endpoint necesitamos rol ADMIN
        Mockito.when(jwtUtil.extractRole(FAKE_TOKEN)).thenReturn("ADMIN");
        Mockito.when(ownerService.saveOwner(any(UserRequestDto.class))).thenReturn(mockResponse);

        UserRequestDto dto = new UserRequestDto(
                "Juan", "Uribe", "12345678", "juan@example.com",
                "1042241877Ju@n", "3001234567", LocalDate.of(2000,1,1), 2L
        );

        mockMvc.perform(post("/users/owner")
                        .header("Authorization", "Bearer " + FAKE_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.role").value("OWNER"));
    }

    @Test
    void createClient_shouldReturnCreatedUser() throws Exception {
        // Este endpoint es permitAll, con OWNER stub vale
        Mockito.when(clientService.saveClient(any(UserRequestDto.class))).thenReturn(mockResponse);

        UserRequestDto dto = new UserRequestDto(
                "Juan", "Uribe", "12345678", "juan@example.com",
                "1042241877Ju@n", "3001234567", LocalDate.of(2000,1,1), 4L
        );

        mockMvc.perform(post("/users/client")
                        .header("Authorization", "Bearer " + FAKE_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("juan@example.com"));
    }

    @Test
    void createEmployee_shouldReturnCreatedUser() throws Exception {
        // PreAuthorize("hasRole('OWNER')") → OWNER stub funciona
        Mockito.when(employeeService.saveEmployee(any(CreateEmployeeRequestDto.class))).thenReturn(mockResponse);

        CreateEmployeeRequestDto dto = new CreateEmployeeRequestDto(
                "Juan", "Uribe", "12345678", "juan@example.com",
                "pass", "3001234567", LocalDate.of(2000,1,1), 99L
        );

        mockMvc.perform(post("/users/employee")
                        .header("Authorization", "Bearer " + FAKE_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("OWNER"));
    }

    @Test
    void existsAndIsOwner_shouldReturnBoolean() throws Exception {
        // Este endpoint necesita ADMIN o adaptar el @PreAuthorize, aquí simulamos ADMIN
        Mockito.when(jwtUtil.extractRole(FAKE_TOKEN)).thenReturn("ADMIN");
        Mockito.when(ownerService.existsAndIsOwner(1L)).thenReturn(true);

        mockMvc.perform(get("/users/1/exists")
                        .header("Authorization", "Bearer " + FAKE_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
