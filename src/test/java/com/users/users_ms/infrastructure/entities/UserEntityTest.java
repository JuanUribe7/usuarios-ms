package com.users.users_ms.infrastructure.entities;

import com.users.users_ms.domain.model.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void defaultConstructorAndSettersGetters() {
        UserEntity entity = new UserEntity();

        // Set values
        entity.setId(10L);
        entity.setName("Carlos");
        entity.setLastName("Lopez");
        entity.setIdentityDocument("ID1234");
        entity.setPhone("3501234567");
        LocalDate date = LocalDate.of(1990, 1, 1);
        entity.setBirthDate(date);
        entity.setEmail("carlos.lopez@example.com");
        entity.setPassword("mypassword");
        entity.setRole(Role.ADMIN);

        // Assert values
        assertEquals(10L, entity.getId());
        assertEquals("Carlos", entity.getName());
        assertEquals("Lopez", entity.getLastName());
        assertEquals("ID1234", entity.getIdentityDocument());
        assertEquals("3501234567", entity.getPhone());
        assertEquals(date, entity.getBirthDate());
        assertEquals("carlos.lopez@example.com", entity.getEmail());
        assertEquals("mypassword", entity.getPassword());
        assertEquals(Role.ADMIN, entity.getRole());
    }

    @Test
    void allArgsConstructorAndGetters() {
        LocalDate birth = LocalDate.of(1985, 12, 31);
        UserEntity entity = new UserEntity(
                20L,
                "Laura",
                "Martinez",
                "DOC5678",
                "3107654321",
                birth,
                "laura.martinez@example.com",
                "securepwd",
                Role.OWNER
        );

        // Assert via getters
        assertEquals(20L, entity.getId());
        assertEquals("Laura", entity.getName());
        assertEquals("Martinez", entity.getLastName());
        assertEquals("DOC5678", entity.getIdentityDocument());
        assertEquals("3107654321", entity.getPhone());
        assertEquals(birth, entity.getBirthDate());
        assertEquals("laura.martinez@example.com", entity.getEmail());
        assertEquals("securepwd", entity.getPassword());
        assertEquals(Role.OWNER, entity.getRole());
    }
}
