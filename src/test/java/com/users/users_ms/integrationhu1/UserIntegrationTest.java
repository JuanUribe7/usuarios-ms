package com.users.users_ms.integrationhu1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenCreateUser_thenUserIsPersisted() throws Exception {
        UserRequestDto dto = new UserRequestDto();
        dto.setName("Integration");
        dto.setLastName("Test");
        dto.setIdentityDocument("555555555");
        dto.setPhone("+573005698325");
        dto.setEmail("integration@example.com");
        dto.setPassword("StrongPassword@123");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));

        mockMvc.perform(post("/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Integration"))
                .andExpect(jsonPath("$.email").value("integration@example.com"));
}}
