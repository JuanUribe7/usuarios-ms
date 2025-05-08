package com.users.users_ms.infrastructure.adapters.persistence;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.infrastructure.entities.UserEntity;
import com.users.users_ms.infrastructure.mappers.UserEntityMapper;
import com.users.users_ms.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserJpaAdapterTest {

    private UserRepository repo;
    private UserJpaAdapter adapter;

    @BeforeEach
    void setUp() {
        repo = mock(UserRepository.class);
        adapter = new UserJpaAdapter(repo);
    }

    private User sampleUser() {
        return new User(
                1L, "Juan", "Perez", "12345678",
                "a@b.com", "pass", "+1234567890",
                LocalDate.of(1990, 1, 1), null
        );
    }

    private UserEntity sampleEntity() {
        var e = new UserEntity(
                1L, "Juan", "Perez", "12345678",
                "+1234567890", LocalDate.of(1990,1,1),
                "a@b.com", "pass", null
        );
        e.setRole(null);
        return e;
    }

    @Test
    void saveUser_callsRepositoryAndReturnsMapped() {
        User user = sampleUser();
        UserEntity entity = sampleEntity();

        try (MockedStatic<UserEntityMapper> m = mockStatic(UserEntityMapper.class)) {
            m.when(() -> UserEntityMapper.toEntity(user)).thenReturn(entity);
            when(repo.save(entity)).thenReturn(entity);           // ← AQUÍ
            m.when(() -> UserEntityMapper.toModel(entity)).thenReturn(user);

            User result = adapter.saveUser(user);

            verify(repo).save(entity);
            assertSame(user, result);
        }
    }

    @Test
    void updateUser_savesMappedEntity() {
        User user = sampleUser();
        UserEntity entity = sampleEntity();

        try (MockedStatic<UserEntityMapper> m = mockStatic(UserEntityMapper.class)) {
            m.when(() -> UserEntityMapper.toEntity(user)).thenReturn(entity);

            adapter.updateUser(user);

            verify(repo).save(entity);
        }
    }

    @Test
    void findByEmail_present_returnsMapped() {
        String email = "a@b.com";
        User user = sampleUser();
        UserEntity entity = sampleEntity();

        when(repo.findByEmail(email)).thenReturn(Optional.of(entity));
        try (MockedStatic<UserEntityMapper> m = mockStatic(UserEntityMapper.class)) {
            m.when(() -> UserEntityMapper.toModel(entity)).thenReturn(user);

            Optional<User> opt = adapter.findByEmail(email);

            assertTrue(opt.isPresent());
            assertSame(user, opt.get());
        }
    }

    @Test
    void findByEmail_empty_returnsEmpty() {
        when(repo.findByEmail("nope")).thenReturn(Optional.empty());
        Optional<User> opt = adapter.findByEmail("nope");
        assertTrue(opt.isEmpty());
    }

    @Test
    void existsByEmail_delegates() {
        when(repo.existsByEmail("x@y.com")).thenReturn(true);
        assertTrue(adapter.existsByEmail("x@y.com"));

        when(repo.existsByEmail("x@y.com")).thenReturn(false);
        assertFalse(adapter.existsByEmail("x@y.com"));
    }

    @Test
    void findById_present_returnsMapped() {
        long id = 42L;
        UserEntity entity = sampleEntity();
        User user = sampleUser();

        when(repo.findById(id)).thenReturn(Optional.of(entity));
        try (MockedStatic<UserEntityMapper> m = mockStatic(UserEntityMapper.class)) {
            m.when(() -> UserEntityMapper.toModel(entity)).thenReturn(user);

            Optional<User> opt = adapter.findById(id);

            assertTrue(opt.isPresent());
            assertSame(user, opt.get());
        }
    }

    @Test
    void findById_empty_returnsEmpty() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        Optional<User> opt = adapter.findById(99L);
        assertTrue(opt.isEmpty());
    }
}