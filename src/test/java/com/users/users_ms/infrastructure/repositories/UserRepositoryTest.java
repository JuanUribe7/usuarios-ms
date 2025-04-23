package com.users.users_ms.infrastructure.repositories;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.infrastructure.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    private UserEntity savedUser;

    @BeforeEach
    void setUp() {
        UserEntity user = new UserEntity();
        user.setName("Test");
        user.setLastName("User");
        user.setIdentityDocument("987654321");
        user.setEmail("test@example.com");
        user.setPhone("+573005698325");
        user.setBirthDate(LocalDate.of(1995, 1, 1));
        user.setPassword("encryptedPassword");
        user.setRole(Role.OWNER);

        savedUser = userRepository.save(user);
    }

    @Test
    void whenFindByEmail_thenReturnUser() {
        Optional<UserEntity> result = userRepository.findByEmail("test@example.com");
        assertTrue(result.isPresent());
        assertEquals(savedUser.getEmail(), result.get().getEmail());
    }

    @Test
    void whenFindByIdentityDocument_thenReturnUser() {
        Optional<UserEntity> result = userRepository.findByIdentityDocument("987654321");
        assertTrue(result.isPresent());
        assertEquals(savedUser.getIdentityDocument(), result.get().getIdentityDocument());
    }

    @Test
    void whenFindByEmail_NotExists_thenReturnEmpty() {
        Optional<UserEntity> result = userRepository.findByEmail("nonexistent@example.com");
        assertFalse(result.isPresent());
    }

    @Test
    void whenFindByIdentityDocument_NotExists_thenReturnEmpty() {
        Optional<UserEntity> result = userRepository.findByIdentityDocument("123123123");
        assertFalse(result.isPresent());
    }
}