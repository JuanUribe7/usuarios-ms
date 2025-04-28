package com.users.users_ms.infrastructure.adapters.client;

import com.users.users_ms.infrastructure.exceptions.RestaurantNotFoundException;
import feign.FeignException;
import feign.Request;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CustomErrorDecoderTest {
    private final ErrorDecoder decoder = new CustomErrorDecoder();

    @Test
    void decode_ShouldReturnRestaurantNotFoundException_For404() {
        Response response = Response.builder()
                .status(404)
                .reason("Not Found")
                .request(Request.create("GET", "/test", Collections.emptyMap(), null, Charset.defaultCharset()))
                .build();

        Exception ex = decoder.decode("methodKey", response);

        assertNotNull(ex);
        assertTrue(ex instanceof RestaurantNotFoundException, "Debe ser RestaurantNotFoundException");
        assertEquals("El propietario no fue encontrado.", ex.getMessage(), "Mensaje incorrecto para status 404");
    }

    @Test
    void decode_ShouldReturnRuntimeException_For500() {
        Response response = Response.builder()
                .status(500)
                .reason("Internal Server Error")
                .request(Request.create("GET", "/test", Collections.emptyMap(), null, Charset.defaultCharset()))
                .build();

        Exception ex = decoder.decode("methodKey", response);

        assertNotNull(ex);
        assertTrue(ex instanceof RuntimeException, "Debe ser RuntimeException");
        assertEquals("No existe un usuario con ese id.", ex.getMessage(), "Mensaje incorrecto para status 500");
    }

    @Test
    void decode_ShouldReturnFeignException_ForOtherStatus() {
        Response response = Response.builder()
                .status(400)
                .reason("Bad Request")
                .request(Request.create("GET", "/test", Collections.emptyMap(), null, Charset.defaultCharset()))
                .build();

        Exception ex = decoder.decode("methodKey", response);

        assertNotNull(ex, "La excepción no debe ser nula");
        assertTrue(ex instanceof FeignException, "Debe devolver FeignException para otros status");
        FeignException fe = (FeignException) ex;
        assertEquals(400, fe.status(), "El status debe ser 400");
    }
}
