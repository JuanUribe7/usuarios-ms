package com.users.users_ms;

import com.users.users_ms.domain.model.User;
import com.users.users_ms.infrastructure.entities.UserEntity;
import com.users.users_ms.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
public class UserIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUserIntegrationTest() throws Exception {
        String userJson = "{ " +
                "\"name\": \"Carlos\", " +
                "\"lastName\": \"Gomez\", " +
                "\"email\": \"carlos@example.com\", " +
                "\"phone\": \"+573001234567\", " +
                "\"identityDocument\": \"123456789\", " +
                "\"birthDate\": \"1990-01-01\", " +
                "\"password\": \"Password1!\" " +
                "}";

        mockMvc.perform(post("/user/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("carlos@example.com"))
                .andExpect(jsonPath("$.role").value("OWNER"));

        Optional<UserEntity> optionalUser = userRepository.findByEmail("carlos@example.com");
        assertTrue(optionalUser.isPresent());
        UserEntity savedUser = optionalUser.get();
        assertEquals("carlos@example.com", savedUser.getEmail());
    }
}