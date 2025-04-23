package com.users.users_ms.domain.usecases;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class UserUseCaseTest {

    private UserUseCase userUseCase;
    private UserPersistencePort userPersistencePort;

    @BeforeEach
    void setUp() {
        userPersistencePort = mock(UserPersistencePort.class);
        userUseCase = new UserUseCase(userPersistencePort);
    }

    @Test
    void saveOwner_ShouldAssignOwnerRoleAndPersist() {
        User user = new User();
        user.setName("Carlos");
        user.setEmail("carlos@example.com");

        User persistedUser = new User();
        persistedUser.setId(1L);
        persistedUser.setName("Carlos");
        persistedUser.setEmail("carlos@example.com");
        persistedUser.setRole(Role.OWNER);

        when(userPersistencePort.saveUser(any(User.class))).thenReturn(persistedUser);

        User result = userUseCase.saveOwner(user);

        assertNotNull(result);
        assertEquals(Role.OWNER, result.getRole());
        verify(userPersistencePort, times(1)).saveUser(user);
    }

}