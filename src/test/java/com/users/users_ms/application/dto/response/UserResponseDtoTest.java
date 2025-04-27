// src/test/java/com/users/users_ms/application/dto/response/UserResponseDtoTest.java

package com.users.users_ms.application.dto.response;

import com.users.users_ms.domain.model.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseDtoTest {

    @Test
    void testSettersAndGetters() {
        UserResponseDto user = new UserResponseDto();
        Role role = Role.OWNER;

        user.setId(1L);
        user.setName("Juan");
        user.setLastName("Pérez");
        user.setIdentityDocument("12345678");
        user.setEmail("juan@example.com");
        user.setRole(role);

        assertEquals(1L, user.getId());
        assertEquals("Juan", user.getName());
        assertEquals("Pérez", user.getLastName());
        assertEquals("12345678", user.getIdentityDocument());
        assertEquals("juan@example.com", user.getEmail());
        assertEquals(Role.OWNER, user.getRole());
    }
}
