package com.users.users_ms.infrastructure.adapters.security;

import com.users.users_ms.domain.model.LoginResponse;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.AuthenticationPort;
import com.users.users_ms.domain.services.AuthenticationService;
import com.users.users_ms.infrastructure.security.JwtUtil;
import com.users.users_ms.infrastructure.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticationAdapter implements AuthenticationPort {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final AuthenticationService authenticationService;

    @Override
    public LoginResponse authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = authenticationService.validateAndGetUser(email);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String jwt = jwtUtil.generateToken(userDetails, user.getId(), user.getRole().name());
        return authenticationService.createLoginResponse(jwt, user.getEmail(), user.getRole().name());
    }
}