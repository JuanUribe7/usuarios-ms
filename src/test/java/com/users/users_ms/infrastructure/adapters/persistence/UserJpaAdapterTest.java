package com.users.users_ms.infrastructure.adapters.persistence;

import com.users.users_ms.domain.exceptions.InvalidEmailException;
import com.users.users_ms.domain.exceptions.InvalidIdentityDocumentException;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.infrastructure.entities.UserEntity;
import com.users.users_ms.infrastructure.mappers.UserEntityMapper;
import com.users.users_ms.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserJpaAdapterTest {

    private UserRepository userRepository;
    private UserEntityMapper mapper;
    private UserJpaAdapter adapter;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        mapper = mock(UserEntityMapper.class);
        adapter = new UserJpaAdapter(userRepository, mapper);
    }

    @Test
    void testSaveUser_Success() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setIdentityDocument("123456");

        UserEntity entity = new UserEntity();
        UserEntity savedEntity = new UserEntity();
        User mappedUser = new User();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByIdentityDocument(user.getIdentityDocument())).thenReturn(Optional.empty());
        when(mapper.toEntity(user)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.toModel(savedEntity)).thenReturn(mappedUser);

        User result = adapter.saveUser(user);
        assertEquals(mappedUser, result);
    }

    @Test
    void testSaveUser_EmailExists_ThrowsException() {
        User user = new User();
        user.setEmail("exists@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserEntity()));

        assertThrows(InvalidEmailException.class, () -> adapter.saveUser(user));
    }

    @Test
    void testSaveUser_IdentityExists_ThrowsException() {
        User user = new User();
        user.setEmail("unique@example.com");
        user.setIdentityDocument("exist-doc");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByIdentityDocument(user.getIdentityDocument())).thenReturn(Optional.of(new UserEntity()));

        assertThrows(InvalidIdentityDocumentException.class, () -> adapter.saveUser(user));
    }

    @Test
    void testUpdateUser_CallsSave() {
        User user = new User();
        UserEntity entity = new UserEntity();

        when(mapper.toEntity(user)).thenReturn(entity);

        adapter.updateUser(user);

        verify(userRepository).save(entity);
    }

    @Test
    void testFindByEmail_ReturnsMappedUser() {
        String email = "search@example.com";
        UserEntity entity = new UserEntity();
        User user = new User();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(entity));
        when(mapper.toModel(entity)).thenReturn(user);

        Optional<User> result = adapter.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindById_ReturnsMappedUser() {
        Long id = 1L;
        UserEntity entity = new UserEntity();
        User user = new User();

        when(userRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toModel(entity)).thenReturn(user);

        Optional<User> result = adapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindAll_ReturnsMappedUsers() {
        List<UserEntity> entities = List.of(new UserEntity(), new UserEntity());
        List<User> users = List.of(new User(), new User());

        when(userRepository.findAll()).thenReturn(entities);
        when(mapper.toModelList(entities)).thenReturn(users);

        List<User> result = adapter.findAll();

        assertEquals(users.size(), result.size());
    }

}