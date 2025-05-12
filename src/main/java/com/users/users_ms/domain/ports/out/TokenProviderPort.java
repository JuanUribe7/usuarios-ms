package com.users.users_ms.domain.ports.out;

import com.users.users_ms.domain.model.UserDetailsDomain;

public interface TokenProviderPort {
    String generateToken(UserDetailsDomain userDetails, Long userId, String role);
}