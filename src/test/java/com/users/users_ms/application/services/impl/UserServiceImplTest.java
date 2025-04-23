package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.UserServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserServiceImpl userService;
    private UserServicePort userServicePort;
    private UserDtoMapper mapper;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userServicePort = mock(UserServicePort.class);
        mapper = mock(UserDtoMapper.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userServicePort, mapper, passwordEncoder);
    }

    @Test
    void saveOwner_ShouldEncodePasswordAndDelegateToUseCase() {
        // Arrange
        UserRequestDto dto = new UserRequestDto();
        dto.setName("Mario");
        dto.setPassword("raw-password");

        User mappedUser = new User();
        mappedUser.setName("Mario");
        mappedUser.setPassword("raw-password");

        User persistedUser = new User();
        persistedUser.setId(1L);
        persistedUser.setName("Mario");
        persistedUser.setPassword("encoded-password");
        persistedUser.setRole(Role.OWNER);

        when(mapper.toModel(dto)).thenReturn(mappedUser);
        when(passwordEncoder.encode("raw-password")).thenReturn("encoded-password");
        when(userServicePort.saveOwner(any(User.class))).thenReturn(persistedUser);

        // Act
        User result = userService.saveOwner(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Mario", result.getName());
        assertEquals("encoded-password", result.getPassword());
        assertEquals(Role.OWNER, result.getRole());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userServicePort).saveOwner(userCaptor.capture());
        assertEquals("encoded-password", userCaptor.getValue().getPassword());
    }

}