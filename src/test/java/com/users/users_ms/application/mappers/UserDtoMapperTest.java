// src/test/java/com/users/users_ms/application/mappers/UserDtoMapperTest.java
package com.users.users_ms.application.mappers;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.request.CreateEmployeeRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.model.Role;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoMapperTest {

    @Test
    void constructorThrowsWrapped() throws Exception {
        Constructor<UserDtoMapper> ctor = UserDtoMapper.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        InvocationTargetException ite = assertThrows(InvocationTargetException.class, ctor::newInstance);
        Throwable cause = ite.getCause();
        assertTrue(cause instanceof IllegalStateException);
        assertEquals(ExceptionMessages.UTILITY_CLASS_INSTANTIATION, cause.getMessage());
    }

    @Test
    void toModel_fromUserRequestDto() {
        LocalDate birth = LocalDate.of(1995, 5, 20);
        UserRequestDto dto = new UserRequestDto(
                "Juan", "Pérez", "12345678",
                "juan@example.com", "Pwd123!", "3001234567",
                birth, 2L
        );

        User user = UserDtoMapper.toModel(dto);

        assertNull(user.getId());
        assertEquals("Juan", user.getName());
        assertEquals("Pérez", user.getLastName());
        assertEquals("12345678", user.getIdentityDocument());
        assertEquals("juan@example.com", user.getEmail());
        assertEquals("Pwd123!", user.getPassword());
        assertEquals("3001234567", user.getPhone());
        assertEquals(birth, user.getBirthDate());
        assertNull(user.getRole());
    }

    @Test
    void toModel_fromCreateEmployeeRequestDto() {
        LocalDate birth = LocalDate.of(1990, 1, 1);
        CreateEmployeeRequestDto dto = new CreateEmployeeRequestDto(
                "Ana", "Gómez", "87654321",
                "ana@example.com", "Secret1!", "3107654321",
                birth, 55L
        );

        User user = UserDtoMapper.toModel(dto);

        assertNull(user.getId());
        assertEquals("Ana", user.getName());
        assertEquals("Gómez", user.getLastName());
        assertEquals("87654321", user.getIdentityDocument());
        assertEquals("ana@example.com", user.getEmail());
        assertEquals("Secret1!", user.getPassword());
        assertEquals("3107654321", user.getPhone());
        assertEquals(birth, user.getBirthDate());
        assertNull(user.getRole());
    }

    @Test
    void toDto_fromUser() {
        Role role = Role.EMPLOYEE;
        User user = new User(
                7L, "Luis", "Martínez", "11223344",
                "luis@example.com", "Pwd!234", "3201122334",
                LocalDate.of(1985, 12, 5), role
        );

        UserResponseDto dto = UserDtoMapper.toDto(user);

        assertEquals(7L, dto.getId());
        assertEquals("Luis", dto.getName());
        assertEquals("Martínez", dto.getLastName());
        assertEquals("11223344", dto.getIdentityDocument());
        assertEquals("luis@example.com", dto.getEmail());
        assertEquals(role, dto.getRole());
    }
}
