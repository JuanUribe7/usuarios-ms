package com.users.users_ms.infrastructure.endpoints.rest;

import com.users.users_ms.application.dto.request.LoginRequestDto;
import com.users.users_ms.application.dto.response.LoginResponseDto;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import com.users.users_ms.infrastructure.security.JwtUtil;
import com.users.users_ms.infrastructure.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserPersistencePort userPersistencePort;

    @InjectMocks
    private AuthController authController;

    private final String email = "juan@example.com";
    private final String password = "secret";

    @Test
    void login_Success() {
        // Arrange
        LoginRequestDto request = new LoginRequestDto(email, password);
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(password)
                .roles("ADMIN")
                .build();
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);

        User domainUser = new User();
        domainUser.setId(1L);
        domainUser.setEmail(email);
        domainUser.setRole(Role.ADMIN);
        when(userPersistencePort.findByEmail(email)).thenReturn(Optional.of(domainUser));

        when(jwtUtil.generateToken(userDetails, 1L, "ADMIN")).thenReturn("token123");

        // Act
        ResponseEntity<LoginResponseDto> response = authController.login(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        LoginResponseDto body = response.getBody();
        assertNotNull(body, "El cuerpo de la respuesta no debe ser nulo");
        assertEquals("token123", body.getToken(), "El token no coincide");
        assertEquals(email, body.getEmail(), "El email no coincide");
        assertEquals("ADMIN", body.getRole(), "El rol no coincide");

        // Verify interactions
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername(email);
        verify(userPersistencePort).findByEmail(email);
        verify(jwtUtil).generateToken(userDetails, 1L, "ADMIN");
    }

    @Test
    void login_UserNotFound_ShouldThrowException() {
        // Arrange
        LoginRequestDto request = new LoginRequestDto(email, password);
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(password)
                .roles("ADMIN")
                .build();
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(userPersistencePort.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException ex = assertThrows(
                UsernameNotFoundException.class,
                () -> authController.login(request),
                "Debe lanzar UsernameNotFoundException cuando no se encuentra el usuario"
        );
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void login_InvalidCredentials_ShouldThrowBadCredentials() {
        // Arrange
        LoginRequestDto request = new LoginRequestDto(email, password);
        doThrow(new BadCredentialsException("Credenciales inválidas"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act & Assert
        BadCredentialsException ex = assertThrows(
                BadCredentialsException.class,
                () -> authController.login(request),
                "Debe lanzar BadCredentialsException para credenciales inválidas"
        );
        assertEquals("Credenciales inválidas", ex.getMessage());
    }
}
