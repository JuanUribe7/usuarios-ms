package com.users.users_ms.infrastructure.adapters.security;

import com.users.users_ms.domain.model.UserDetailsDomain;
import com.users.users_ms.domain.ports.out.TokenProviderPort;
import com.users.users_ms.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@RequiredArgsConstructor
@Component
public class JwtTokenProviderAdapter implements TokenProviderPort {
    private final JwtUtil jwtUtil;

    @Override
    public String generateToken(UserDetailsDomain userDetails, Long userId, String role) {

        UserDetails springUserDetails = User.withUsername(userDetails.getEmail())
                .password("")
                .authorities(Collections.emptyList())
                .build();

        return jwtUtil.generateToken(springUserDetails, userId, role);
    }
}