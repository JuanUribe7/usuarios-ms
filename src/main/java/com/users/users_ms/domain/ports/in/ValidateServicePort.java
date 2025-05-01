package com.users.users_ms.domain.ports.in;

public interface ValidateServicePort {
    boolean existsAndIsOwner(Long id);
}
