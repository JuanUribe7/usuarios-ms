package com.users.users_ms.infrastructure.configuration;

import com.users.users_ms.infrastructure.adapters.client.CustomErrorDecoder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeignConfigTest {

    private FeignConfig feignConfig;

    @BeforeEach
    void setUp() {
        feignConfig = new FeignConfig();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void errorDecoder_shouldReturnCustomErrorDecoder() {
        ErrorDecoder decoder = feignConfig.errorDecoder();
        assertNotNull(decoder, "El decodificador no debe ser nulo");
        assertTrue(decoder instanceof CustomErrorDecoder, "Debe ser instancia de CustomErrorDecoder");
    }

    @Test
    void requestInterceptor_withAuthenticationAndCredentials_shouldAddHeader() {
        // Arrange
        Authentication auth = mock(Authentication.class);
        when(auth.getCredentials()).thenReturn("mytoken");
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        RequestInterceptor interceptor = feignConfig.requestInterceptor();
        RequestTemplate template = new RequestTemplate();

        // Act
        interceptor.apply(template);

        // Assert
        Collection<String> header = template.headers().get("Authorization");
        assertNotNull(header, "Debe haberse agregado la cabecera Authorization");
        assertEquals(1, header.size(), "Debe haber exactamente un valor en la cabecera");
        assertTrue(header.contains("Bearer mytoken"), "El valor de la cabecera no coincide");
    }

    @Test
    void requestInterceptor_noAuthentication_shouldNotAddHeader() {
        // Arrange: contexto sin Authentication
        SecurityContextHolder.setContext(mock(SecurityContext.class));

        RequestInterceptor interceptor = feignConfig.requestInterceptor();
        RequestTemplate template = new RequestTemplate();

        // Act
        interceptor.apply(template);

        // Assert
        assertFalse(template.headers().containsKey("Authorization"),
                "No debe agregarse la cabecera Authorization si no hay autenticación");
    }

    @Test
    void requestInterceptor_authWithoutCredentials_shouldNotAddHeader() {
        // Arrange: Authentication sin credenciales
        Authentication auth = mock(Authentication.class);
        when(auth.getCredentials()).thenReturn(null);
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        RequestInterceptor interceptor = feignConfig.requestInterceptor();
        RequestTemplate template = new RequestTemplate();

        // Act
        interceptor.apply(template);

        // Assert
        assertFalse(template.headers().containsKey("Authorization"),
                "No debe agregarse la cabecera Authorization si las credenciales son nulas");
    }
}
