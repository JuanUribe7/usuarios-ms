package com.users.users_ms.application.mappers;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.domain.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoMapperTest {
    private final UserDtoMapper userMapper;
    public UserDtoMapperTest() {
        userMapper = new UserDtoMapperImpl();
    }

    @Test
    void testUserToUserRequestDtoMapping() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test");
        user.setLastName("User");

        UserResponseDto dto = userMapper.toResponseDto(user);

        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getLastName(), dto.getLastName());
    }

}