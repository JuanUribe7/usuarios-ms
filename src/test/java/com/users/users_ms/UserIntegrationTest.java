package com.users.users_ms;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.domain.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Key;
import java.util.Date;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private com.users.users_ms.application.services.UserService userService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    private String adminToken;

    @BeforeEach
    void setUp() {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        adminToken = Jwts.builder()
                .setSubject("admin")
                .claim("userId", 1L)
                .claim("role", "ADMIN")
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();
    }

    @Test
    @DisplayName("POST /user/owner - éxito")
    void createOwner_Success() throws Exception {
        UserRequestDto dto = new UserRequestDto();
        dto.setName("Juan");
        dto.setLastName("Perez");
        dto.setEmail("juan@example.com");
        dto.setPassword("secret");
        when(userService.saveOwner(dto)).thenReturn(null);

        mockMvc.perform(post("/user/owner")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /user/owner - error de validación 400")
    void createOwner_ValidationError() throws Exception {
        UserRequestDto dto = new UserRequestDto();

        mockMvc.perform(post("/user/owner")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("El nombre no puede estar vacío"));
    }

    @Test
    @DisplayName("GET /user/{id}/rol - éxito")
    void getUserRoleById_Success() throws Exception {
        Long userId = 1L;
        when(userService.getUserRoleById(userId)).thenReturn("OWNER");

        mockMvc.perform(get("/user/{id}/rol", userId)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("OWNER"));
    }

    @Test
    @DisplayName("GET /user/{id}/rol - no autorizado 401")
    void getUserRoleById_Unauthorized() throws Exception {
        Long userId = 2L;
        doThrow(new UnauthorizedException("No autorizado"))
                .when(userService).getUserRoleById(userId);

        mockMvc.perform(get("/user/{id}/rol", userId)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("No autorizado"));
    }
}