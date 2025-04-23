package com.users.users_ms.application.mappers;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoMapperTest {

        private UserDtoMapper mapper;

        @BeforeEach
        void setUp() {
            mapper = Mappers.getMapper(UserDtoMapper.class);
        }

        @Test
        void testMapToModel() {
            UserRequestDto dto = new UserRequestDto();
            dto.setName("Juan");
            dto.setLastName("Perez");
            dto.setIdentityDocument("12345678");
            dto.setPhone("+573005698325");
            dto.setEmail("juan@example.com");
            dto.setPassword("clave");

            User user = mapper.toModel(dto);

            assertNotNull(user);
            assertEquals("Juan", user.getName());
            assertEquals("Perez", user.getLastName());
            assertEquals("12345678", user.getIdentityDocument());
            assertEquals("+573005698325", user.getPhone());
            assertEquals("juan@example.com", user.getEmail());
            assertEquals("clave", user.getPassword()); // No encriptado aún
        }

        @Test
        void testMapToResponseDto() {
            User user = new User();
            user.setId(1L);
            user.setName("Ana");
            user.setLastName("Lopez");
            user.setIdentityDocument("87654321");
            user.setEmail("ana@example.com");
            user.setRole(Role.OWNER);

            UserResponseDto responseDto = mapper.toResponseDto(user);

            assertNotNull(responseDto);
            assertEquals(1L, responseDto.getId());
            assertEquals("Ana", responseDto.getName());
            assertEquals("Lopez", responseDto.getLastName());
            assertEquals("87654321", responseDto.getIdentityDocument());
            assertEquals("ana@example.com", responseDto.getEmail());
            assertEquals(Role.OWNER, responseDto.getRole());
        }

        @Test
        void testMapToModelWithNulls() {
            UserRequestDto dto = new UserRequestDto();
            dto.setName(null);
            dto.setLastName(null);
            dto.setIdentityDocument(null);
            dto.setPhone(null);
            dto.setEmail(null);
            dto.setPassword(null);

            User user = mapper.toModel(dto);

            assertNotNull(user);
            assertNull(user.getName());
            assertNull(user.getLastName());
            assertNull(user.getIdentityDocument());
            assertNull(user.getPhone());
            assertNull(user.getEmail());
            assertNull(user.getPassword());
        }
    }

