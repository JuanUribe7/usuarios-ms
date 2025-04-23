package com.users.users_ms.infrastructure.mappers;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.infrastructure.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityMapperTest {

    private UserEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UserEntityMapper.class);
    }

    @Test
    void testToEntity_AllFieldsMappedCorrectly() {
        User user = new User();
        user.setId(1L);
        user.setName("Carlos");
        user.setLastName("Ramirez");
        user.setIdentityDocument("98765432");
        user.setPhone("+573001112233");
        user.setEmail("carlos@example.com");
        user.setPassword("clave123");
        user.setRole(Role.CLIENT);

        UserEntity entity = mapper.toEntity(user);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Carlos", entity.getName());
        assertEquals("Ramirez", entity.getLastName());
        assertEquals("98765432", entity.getIdentityDocument());
        assertEquals("+573001112233", entity.getPhone());
        assertEquals("carlos@example.com", entity.getEmail());
        assertEquals("clave123", entity.getPassword());
        assertEquals(Role.CLIENT, entity.getRole());
    }

    @Test
    void testToModel_AllFieldsMappedCorrectly() {
        UserEntity entity = new UserEntity();
        entity.setId(2L);
        entity.setName("Laura");
        entity.setLastName("Gomez");
        entity.setIdentityDocument("22334455");
        entity.setPhone("+573001234567");
        entity.setEmail("laura@example.com");
        entity.setPassword("clave456");
        entity.setRole(Role.EMPLOYEE);

        User user = mapper.toModel(entity);

        assertNotNull(user);
        assertEquals(2L, user.getId());
        assertEquals("Laura", user.getName());
        assertEquals("Gomez", user.getLastName());
        assertEquals("22334455", user.getIdentityDocument());
        assertEquals("+573001234567", user.getPhone());
        assertEquals("laura@example.com", user.getEmail());
        assertEquals("clave456", user.getPassword());
        assertEquals(Role.EMPLOYEE, user.getRole());
    }

    @Test
    void testToEntity_NullFieldsHandledGracefully() {
        User user = new User();  // All fields null

        UserEntity entity = mapper.toEntity(user);

        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNull(entity.getLastName());
        assertNull(entity.getIdentityDocument());
        assertNull(entity.getPhone());
        assertNull(entity.getEmail());
        assertNull(entity.getPassword());
        assertNull(entity.getRole());
    }

    @Test
    void testToModel_NullFieldsHandledGracefully() {
        UserEntity entity = new UserEntity(); // All fields null

        User user = mapper.toModel(entity);

        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getLastName());
        assertNull(user.getIdentityDocument());
        assertNull(user.getPhone());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }

}