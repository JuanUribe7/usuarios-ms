package com.users.users_ms.domain.ports.out;

public interface PasswordEncoderPort {
    String encodePassword(String rawPassword);
}
