package com.users.users_ms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.users_ms.application.dto.request.CreateOwnerRequestDto;
import com.users.users_ms.application.dto.request.LoginRequestDto;
import com.users.users_ms.commons.constants.ResponseMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void adminLoginAndCreatesOwner() throws Exception {
        // 1. Login como ADMIN
        LoginRequestDto loginDto = new LoginRequestDto("admin@example.com", "admin123");


        String loginResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(loginResponse);
        String token = jsonNode.get("token").asText();

        assertThat(token).isNotBlank();

        // 2. Crear OWNER usando el token
        CreateOwnerRequestDto ownerDto = new CreateOwnerRequestDto("Carlos",
                "Ramirez",
                "1010101010", "carlos20@gmail.com",
                "77182238Ak",
                "+573145062832",
                java.time.LocalDate.of(1990, 5, 1)
                );

        mockMvc.perform(post("/users/owner")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(ResponseMessages.OWNER_CREATED));
    }
}
