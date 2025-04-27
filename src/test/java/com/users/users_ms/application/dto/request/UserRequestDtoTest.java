// src/test/java/com/users/users_ms/application/dto/request/UserRequestDtoTest.java

package com.users.users_ms.application.dto.request;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestDtoTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        UserRequestDto user = new UserRequestDto();
        LocalDate date = LocalDate.of(1990, 1, 1);

        user.setName("Juan");
        user.setLastName("Pérez");
        user.setIdentityDocument("12345678");
        user.setPhone("+573005698325");
        user.setBirthDate(date);
        user.setEmail("juan@example.com");
        user.setPassword("ClaveSegura123!");

        assertEquals("Juan", user.getName());
        assertEquals("Pérez", user.getLastName());
        assertEquals("12345678", user.getIdentityDocument());
        assertEquals("+573005698325", user.getPhone());
        assertEquals(date, user.getBirthDate());
        assertEquals("juan@example.com", user.getEmail());
        assertEquals("ClaveSegura123!", user.getPassword());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDate date = LocalDate.of(1990, 1, 1);
        UserRequestDto user = new UserRequestDto(
                "Juan", "Pérez", "12345678", "+573005698325", date, "juan@example.com", "ClaveSegura123!"
        );

        assertEquals("Juan", user.getName());
        assertEquals("Pérez", user.getLastName());
        assertEquals("12345678", user.getIdentityDocument());
        assertEquals("+573005698325", user.getPhone());
        assertEquals(date, user.getBirthDate());
        assertEquals("juan@example.com", user.getEmail());
        assertEquals("ClaveSegura123!", user.getPassword());
    }
}
