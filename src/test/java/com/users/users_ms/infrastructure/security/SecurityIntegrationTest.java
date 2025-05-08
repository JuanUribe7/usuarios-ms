// src/test/java/com/users/users_ms/infrastructure/security/SecurityIntegrationTest.java
package com.users.users_ms.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.FilterChain;  // <-- paquete corregido

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@TestPropertySource(properties = "jwt.secret-key=0123456789abcdefghijklmnopqrstuv") // 32 chars
class SecurityIntegrationTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtFilter jwtFilter;

    @Test
    void jwtUtil_generateAndExtractClaims() {
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                "test@example.com",
                "irrelevant",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_CLIENT"))
        );
        Long userId = 77L;
        String role = "ROLE_CLIENT";

        String token = jwtUtil.generateToken(userDetails, userId, role);

        assertEquals("test@example.com", jwtUtil.extractUsername(token));
        assertEquals(role, jwtUtil.extractRole(token));
        assertEquals(userId, jwtUtil.extractUserId(token));
    }

    @Test
    void jwtFilter_populatesSecurityContext() throws Exception {
        // Generar token válido
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                "foo@bar.com",
                "pwd",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_OWNER"))
        );
        String token = jwtUtil.generateToken(userDetails, 5L, "ROLE_OWNER");

        // Simular petición con header Authorization
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        SecurityContextHolder.clearContext();
        jwtFilter.doFilterInternal(request, response, chain);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(auth, "El filtro debería haber seteado Authentication");
        assertEquals(token, auth.getCredentials(), "El token debe quedar como credentials");
        assertEquals("foo@bar.com",
                ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());
        assertTrue(auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_OWNER")),
                "Debe contener la autoridad ROLE_OWNER");
    }
}
