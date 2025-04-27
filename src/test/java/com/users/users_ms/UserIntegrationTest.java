package com.users.users_ms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.services.UserService;
import com.users.users_ms.domain.exceptions.UnauthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /user/owner - error de validación 400")
    void createOwner_ValidationError() throws Exception {
        UserRequestDto dto = new UserRequestDto();

        mockMvc.perform(post("/user/owner")
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

        mockMvc.perform(get("/user/{id}/rol", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("OWNER"));
    }

    @Test
    @DisplayName("GET /user/{id}/rol - no autorizado 401")
    void getUserRoleById_Unauthorized() throws Exception {
        Long userId = 2L;
        doThrow(new UnauthorizedException("No autorizado")).when(userService).getUserRoleById(userId);

        mockMvc.perform(get("/user/{id}/rol", userId))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("No autorizado"));
    }
}
