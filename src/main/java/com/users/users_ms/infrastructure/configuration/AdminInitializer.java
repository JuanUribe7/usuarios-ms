package com.users.users_ms.infrastructure.configuration;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.domain.ports.out.PasswordEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public void run(String... args) {
        String adminEmail = "admin@example.com";

        if (!userPersistencePort.existsByEmail(adminEmail)) {
            User admin = new User(
                null,
                "Admin",
                "User",
                "000000000",
                adminEmail,
                passwordEncoderPort.encodePassword("admin123"),
                "+10000000000",
                LocalDate.of(1990, 1, 1),
                Role.ADMIN
            );
            userPersistencePort.saveUser(admin);

        }
    }
}