// src/test/java/com/users/users_ms/infrastructure/adapters/persistence/UserJpaAdapterTest.java

package com.users.users_ms.infrastructure.adapters.persistence;

import com.users.users_ms.domain.exceptions.InvalidEmailException;
import com.users.users_ms.domain.exceptions.InvalidIdentityDocumentException;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.infrastructure.entities.UserEntity;
import com.users.users_ms.infrastructure.mappers.UserEntityMapper;
import com.users.users_ms.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserJpaAdapterTest {

    private UserRepository UserRepository;
    private UserEntityMapper mapper;
    private UserJpaAdapter adapter;

    @BeforeEach
    void setUp() {
        UserRepository = mock(UserRepository.class);
        mapper = mock(UserEntityMapper.class);
        adapter = new UserJpaAdapter(UserRepository, mapper);
    }

    @Test
    void saveUser_ShouldSaveSuccessfully() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setIdentityDocument("12345678");

        UserEntity entity = new UserEntity();
        UserEntity savedEntity = new UserEntity();
        User savedUser = new User();

        when(UserRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(UserRepository.findByIdentityDocument(user.getIdentityDocument())).thenReturn(Optional.empty());
        when(mapper.toEntity(user)).thenReturn(entity);
        when(UserRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.toModel(savedEntity)).thenReturn(savedUser);

        User result = adapter.saveUser(user);
        assertEquals(savedUser, result);
    }

    @Test
    void saveUser_ShouldThrowInvalidEmailException() {
        User user = new User();
        user.setEmail("duplicate@example.com");

        when(UserRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserEntity()));

        assertThrows(InvalidEmailException.class, () -> adapter.saveUser(user));
    }

    @Test
    void saveUser_ShouldThrowInvalidIdentityDocumentException() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setIdentityDocument("12345678");

        when(UserRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(UserRepository.findByIdentityDocument(user.getIdentityDocument())).thenReturn(Optional.of(new UserEntity()));

        assertThrows(InvalidIdentityDocumentException.class, () -> adapter.saveUser(user));
    }

    @Test
    void updateUser_ShouldCallRepositorySave() {
        User user = new User();
        UserEntity entity = new UserEntity();

        when(mapper.toEntity(user)).thenReturn(entity);

        adapter.updateUser(user);

        verify(UserRepository).save(entity);
    }

    @Test
    void findByEmail_ShouldReturnUserIfExists() {
        String email = "test@example.com";
        UserEntity entity = new UserEntity();
        User user = new User();

        when(UserRepository.findByEmail(email)).thenReturn(Optional.of(entity));
        when(mapper.toModel(entity)).thenReturn(user);

        Optional<User> result = adapter.findByEmail(email);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void findAll_ShouldReturnList() {
        when(UserRepository.findAll()).thenReturn(Collections.emptyList());
        when(mapper.toModelList(Collections.emptyList())).thenReturn(Collections.emptyList());

        assertTrue(adapter.findAll().isEmpty());
    }

    @Test
    void findById_ShouldReturnUserIfExists() {
        Long id = 1L;
        UserEntity entity = new UserEntity();
        User user = new User();

        when(UserRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toModel(entity)).thenReturn(user);

        Optional<User> result = adapter.findById(id);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void findByIdentityDocument_ShouldReturnUserIfExists() {
        String doc = "12345678";
        UserEntity entity = new UserEntity();
        User user = new User();

        when(UserRepository.findByIdentityDocument(doc)).thenReturn(Optional.of(entity));
        when(mapper.toModel(entity)).thenReturn(user);

        Optional<User> result = adapter.findByIdentityDocument(doc);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }
}
