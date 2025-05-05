package com.users.users_ms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.commons.constants.RoleConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateOwnerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;



    @Test
    void createOwner_shouldReturn200_whenRequestIsValid() {

        UserRequestDto dto = new UserRequestDto();
        dto.setName("Juan");
        dto.setLastName("Uribe");
        dto.setIdentityDocument("12345678");
        dto.setPhone("3001234567");
        dto.setBirthDate(LocalDate.of(2000, 1, 1));
        dto.setEmail("juan@test.com");
        dto.setPassword("Strong123!");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("role", RoleConstants.ADMIN);

        HttpEntity<UserRequestDto> request = new HttpEntity<>(dto, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "http://localhost:" + port + "/users/owner",
            request,
            String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}