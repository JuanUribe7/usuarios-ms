package com.users.users_ms.application.dto.response;

import com.users.users_ms.domain.model.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserResponseDtoTest {


    @Test
    void testGettersAndSetters() {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(1L);
        dto.setName("Juan");
        dto.setLastName("Perez");
        dto.setIdentityDocument("12345678");
        dto.setEmail("juan@example.com");
        dto.setRole(Role.OWNER);

        assertEquals(1L, dto.getId());
        assertEquals("Juan", dto.getName());
        assertEquals("Perez", dto.getLastName());
        assertEquals("12345678", dto.getIdentityDocument());
        assertEquals("juan@example.com", dto.getEmail());
        assertEquals(Role.OWNER, dto.getRole());
    }

    @Test
    void testConstructorAndFieldAssignment() {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(42L);
        dto.setName("Ana");
        dto.setLastName("Lopez");
        dto.setIdentityDocument("87654321");
        dto.setEmail("ana@correo.com");
        dto.setRole(Role.ADMIN);

        assertAll("Campos asignados correctamente",
                () -> assertEquals(42L, dto.getId()),
                () -> assertEquals("Ana", dto.getName()),
                () -> assertEquals("Lopez", dto.getLastName()),
                () -> assertEquals("87654321", dto.getIdentityDocument()),
                () -> assertEquals("ana@correo.com", dto.getEmail()),
                () -> assertEquals(Role.ADMIN, dto.getRole())
        );
    }

}