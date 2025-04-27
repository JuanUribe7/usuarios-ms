// src/test/java/com/users/users_ms/application/mappers/UserDtoMapperTest.java

package com.users.users_ms.application.mappers;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoMapperTest {

    private final UserDtoMapper mapper = Mappers.getMapper(UserDtoMapper.class);

    @Test
    void testToModel() {
        UserRequestDto dto = new UserRequestDto(
                "Juan", "Pérez", "12345678", "+573005698325",
                LocalDate.of(1990, 1, 1), "juan@example.com", "ClaveSegura123!");

        User user = mapper.toModel(dto);

        assertNull(user.getId());  // porque id se ignora
        assertNull(user.getRole());  // porque role se ignora
        assertEquals("Juan", user.getName());
        assertEquals("Pérez", user.getLastName());
        assertEquals("12345678", user.getIdentityDocument());
        assertEquals("+573005698325", user.getPhone());
        assertEquals(LocalDate.of(1990, 1, 1), user.getBirthDate());
        assertEquals("juan@example.com", user.getEmail());
        assertEquals("ClaveSegura123!", user.getPassword());
    }

    @Test
    void testToResponseDto() {
        User user = new User();
        user.setId(1L);
        user.setName("Ana");
        user.setLastName("García");
        user.setIdentityDocument("87654321");
        user.setEmail("ana@example.com");
        user.setRole(Role.ADMIN);

        UserResponseDto dto = mapper.toResponseDto(user);

        assertEquals(1L, dto.getId());
        assertEquals("Ana", dto.getName());
        assertEquals("García", dto.getLastName());
        assertEquals("87654321", dto.getIdentityDocument());
        assertEquals("ana@example.com", dto.getEmail());
        assertEquals(Role.ADMIN, dto.getRole());
    }
}
