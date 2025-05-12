package com.users.users_ms;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.users_ms.application.dto.request.CreateClientRequestDto;
import com.users.users_ms.application.dto.request.CreateEmployeeRequestDto;
import com.users.users_ms.application.dto.request.CreateOwnerRequestDto;
import com.users.users_ms.application.dto.request.LoginRequestDto;
import com.users.users_ms.commons.constants.ResponseMessages;
import com.users.users_ms.commons.constants.ValidationMessages;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.infrastructure.adapters.client.RestaurantFeignClient;

import com.users.users_ms.infrastructure.entities.UserEntity;
import com.users.users_ms.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private RestaurantFeignClient restaurantClient; // ← este reemplaza WireMock

    @BeforeAll
    void setup() {
        if (userRepository.findByEmail("propietario1@gmail.com").isEmpty()) {
            UserEntity owner = new UserEntity();
            owner.setName("Juan");
            owner.setLastName("Uribe");
            owner.setIdentityDocument("1042241877");
            owner.setPhone("+573000000000");
            owner.setEmail("propietario1@gmail.com");
            owner.setPassword(new BCryptPasswordEncoder().encode("1042241877Ju@n"));
            owner.setBirthDate(LocalDate.of(1990, 1, 1));
            owner.setRole(Role.OWNER);
            userRepository.save(owner);
        }
    }

    @Test
    void adminLoginAndCreatesOwner() throws Exception {
        LoginRequestDto loginDto = new LoginRequestDto("admin@example.com", "admin123");

        String loginResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String token = objectMapper.readTree(loginResponse).get("token").asText();
        assertThat(token).isNotBlank();

        CreateOwnerRequestDto ownerDto = new CreateOwnerRequestDto(
                "Carlos", "Ramirez", "1010101010",
                "carlos20@gmail.com", "77182238Ak",
                "+573145062832", LocalDate.of(1990, 5, 1)
        );

        mockMvc.perform(post("/users/owner")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(ResponseMessages.OWNER_CREATED));
    }

    @Test
    void shouldRejectOwnerCreationWhenUserIsNotAdult() throws Exception {
        LoginRequestDto loginDto = new LoginRequestDto("admin@example.com", "admin123");

        String loginResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String token = objectMapper.readTree(loginResponse).get("token").asText();
        assertThat(token).isNotBlank();

        CreateOwnerRequestDto underageDto = new CreateOwnerRequestDto(
                "Luis", "Torres", "1020304050",
                "luis.underage@gmail.com", "abc1234XYZ",
                "+573145067000", LocalDate.now().minusYears(16)
        );

        mockMvc.perform(post("/users/owner")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(underageDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ValidationMessages.USER_NOT_ADULT)));
    }

    @Test
    void shouldCreateClientSuccessfully() throws Exception {
        CreateClientRequestDto clientDto = new CreateClientRequestDto(
                "Mariana", "Lopez", "7894561230",
                "mariana.client@example.com", "12345Abc",
                "+573155550000", LocalDate.of(1992, 6, 15)
        );

        mockMvc.perform(post("/users/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(ResponseMessages.CLIENT_CREATED));
    }

    @Test
    void ownerLoginAndCreatesEmployeeSuccessfully() throws Exception {
        // Mockear la respuesta del Feign client
        doNothing().when(restaurantClient).validateExists(4L);

        LoginRequestDto loginDto = new LoginRequestDto("propietario1@gmail.com", "1042241877Ju@n");

        String loginResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String token = objectMapper.readTree(loginResponse).get("token").asText();
        assertThat(token).isNotBlank();

        CreateEmployeeRequestDto employeeDto = new CreateEmployeeRequestDto(
                "Andres", "Gomez", "1112223334",
                "andres.employee@example.com", "passW0rd!",
                "+573134567891", LocalDate.of(1990, 3, 20), 4L
        );

        mockMvc.perform(post("/users/employee")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(ResponseMessages.EMPLOYEE_CREATED));
    }
}
