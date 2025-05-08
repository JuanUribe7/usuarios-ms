package com.users.users_ms.domain.model;


import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.validation.MainValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {

    private User baseUser;

    @BeforeEach
    void setUp() {
        baseUser = new User(
                1L,
                "Juan",
                "Uribe",
                "12345678",
                "juan@example.com",
                "password123",
                "3001234567",
                LocalDate.of(2000, 1, 1),
                Role.CLIENT
        );
    }

    @Test
    void createClient_shouldValidateAndSetClientRole() {
        UserPersistencePort port = mock(UserPersistencePort.class);

        try (MockedStatic<MainValidator> mocked = mockStatic(MainValidator.class)) {
            User client = baseUser.createClient(port);

            mocked.verify(() -> MainValidator.validate(baseUser, port));
            assertEquals(Role.CLIENT, client.getRole());
        }
    }

    @Test
    void createOwner_shouldValidateAndSetOwnerRole() {
        UserPersistencePort port = mock(UserPersistencePort.class);

        try (MockedStatic<MainValidator> mocked = mockStatic(MainValidator.class)) {
            User owner = baseUser.createOwner(port);

            mocked.verify(() -> MainValidator.validate(baseUser, port));
            assertEquals(Role.OWNER, owner.getRole());
        }
    }

    @Test
    void createEmployee_shouldValidateAndSetEmployeeRole() {
        UserPersistencePort port = mock(UserPersistencePort.class);

        try (MockedStatic<MainValidator> mocked = mockStatic(MainValidator.class)) {
            User employee = baseUser.createEmployee(port);

            mocked.verify(() -> MainValidator.validate(baseUser, port));
            assertEquals(Role.EMPLOYEE, employee.getRole());
        }
    }

    @Test
    void withEncodedPassword_shouldReturnNewUserWithEncodedPassword() {
        String encodedPass = "encoded123";

        User securedUser = baseUser.withEncodedPassword(encodedPass);

        assertEquals(encodedPass, securedUser.getPassword());
        assertEquals(baseUser.getEmail(), securedUser.getEmail());
        assertEquals(baseUser.getRole(), securedUser.getRole());
        assertNotSame(baseUser, securedUser); // debe ser nueva instancia
    }

    @Test
    void getBirthDateAsLocalDate_shouldReturnSameDate() {
        assertEquals(LocalDate.of(2000, 1, 1), baseUser.getBirthDateAsLocalDate());
    }
}
