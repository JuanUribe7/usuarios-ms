package com.users.users_ms.infrastructure.mappers;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.infrastructure.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityMapperTest {

    private final UserEntityMapper mapper = Mappers.getMapper(UserEntityMapper.class);

    @Test
    void toModel_ShouldMapEntityToModel() {
        // Arrange
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setPassword("secret");
        entity.setName("Juan");
        entity.setLastName("Perez");
        entity.setIdentityDocument("ABC123");
        entity.setPhone("3001234567");
        entity.setBirthDate(LocalDate.of(1995, 5, 20));
        entity.setEmail("juan.perez@example.com");
        entity.setRole(Role.OWNER);



        // Act
        User model = mapper.toModel(entity);

        // Assert
        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getPassword(), model.getPassword());
        assertEquals(entity.getName(), model.getName());
        assertEquals(entity.getLastName(), model.getLastName());
        assertEquals(entity.getIdentityDocument(), model.getIdentityDocument());
        assertEquals(entity.getPhone(), model.getPhone());
        assertEquals(entity.getBirthDate(), model.getBirthDate());
        assertEquals(entity.getEmail(), model.getEmail());
        assertEquals(entity.getRole(), model.getRole());
    }

    @Test
    void toEntity_ShouldMapModelToEntity() {
        // Arrange
        User model = new User();
        model.setId(2L);
        model.setPassword("pwd");
        model.setName("Ana");
        model.setLastName("Gomez");
        model.setIdentityDocument("XYZ789");
        model.setPhone("3107654321");
        model.setBirthDate(LocalDate.of(1988, 12, 10));
        model.setEmail("ana.gomez@example.com");
        model.setRole(Role.ADMIN);

        // Act
        UserEntity entity = mapper.toEntity(model);

        // Assert
        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getPassword(), entity.getPassword());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getLastName(), entity.getLastName());
        assertEquals(model.getIdentityDocument(), entity.getIdentityDocument());
        assertEquals(model.getPhone(), entity.getPhone());
        assertEquals(model.getBirthDate(), entity.getBirthDate());
        assertEquals(model.getEmail(), entity.getEmail());
        assertEquals(model.getRole(), entity.getRole());
    }

    @Test
    void toModelList_ShouldMapListOfEntities() {
        // Arrange
        UserEntity e1 = new UserEntity();
        e1.setId(3L);
        e1.setName("Luis");
        e1.setPassword("pass1");
        // ... set other fields as needed
        UserEntity e2 = new UserEntity();
        e2.setId(4L);
        e2.setName("Maria");
        e2.setPassword("pass2");
        // ... set other fields
        List<UserEntity> entities = Arrays.asList(e1, e2);

        // Act
        List<User> models = mapper.toModelList(entities);

        // Assert
        assertNotNull(models);
        assertEquals(2, models.size());
        assertEquals(e1.getId(), models.get(0).getId());
        assertEquals(e2.getId(), models.get(1).getId());
    }
}
