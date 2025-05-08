// src/test/java/com/users/users_ms/infrastructure/mappers/UserEntityMapperTest.java
package com.users.users_ms.infrastructure.mappers;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.infrastructure.entities.UserEntity;
import com.users.users_ms.infrastructure.mappers.UserEntityMapper;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityMapperTest {

    @Test
    void constructorThrowsWrapped() throws Exception {
        Constructor<UserEntityMapper> ctor = UserEntityMapper.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        InvocationTargetException ite = assertThrows(InvocationTargetException.class, ctor::newInstance);
        Throwable cause = ite.getCause();
        assertTrue(cause instanceof IllegalStateException);
        assertEquals("Utility class", cause.getMessage());
    }

    @Test
    void toEntity_nullUser_returnsNull() {
        assertNull(UserEntityMapper.toEntity(null));
    }

    @Test
    void toEntity_fullUser_mapsAllFields() {
        LocalDate birth = LocalDate.of(1992, 7, 15);
        User user = new User(5L, "Ana", "Lopez", "87654321", "ana@x.com",
                "secret", "+5712345678", birth, Role.OWNER);

        UserEntity entity = UserEntityMapper.toEntity(user);

        assertNotNull(entity);
        assertEquals(5L, entity.getId());
        assertEquals("Ana", entity.getName());
        assertEquals("Lopez", entity.getLastName());
        assertEquals("87654321", entity.getIdentityDocument());
        assertEquals("ana@x.com", entity.getEmail());
        assertEquals("secret", entity.getPassword());
        assertEquals("+5712345678", entity.getPhone());
        assertEquals(birth, entity.getBirthDate());
        assertEquals(Role.OWNER, entity.getRole());
    }

    @Test
    void toModel_nullEntity_returnsNull() {
        assertNull(UserEntityMapper.toModel(null));
    }

    @Test
    void toModel_fullEntity_mapsAllFields() {
        LocalDate birth = LocalDate.of(1988, 3, 10);
        UserEntity entity = new UserEntity();
        entity.setId(8L);
        entity.setName("Luis");
        entity.setLastName("Martinez");
        entity.setIdentityDocument("11223344");
        entity.setEmail("luis@m.com");
        entity.setPassword("pwd");
        entity.setPhone("+5711122233");
        entity.setBirthDate(birth);
        entity.setRole(Role.CLIENT);

        User user = UserEntityMapper.toModel(entity);

        assertNotNull(user);
        assertEquals(8L, user.getId());
        assertEquals("Luis", user.getName());
        assertEquals("Martinez", user.getLastName());
        assertEquals("11223344", user.getIdentityDocument());
        assertEquals("luis@m.com", user.getEmail());
        assertEquals("pwd", user.getPassword());
        assertEquals("+5711122233", user.getPhone());
        assertEquals(birth, user.getBirthDate());
        assertEquals(Role.CLIENT, user.getRole());
    }
}
