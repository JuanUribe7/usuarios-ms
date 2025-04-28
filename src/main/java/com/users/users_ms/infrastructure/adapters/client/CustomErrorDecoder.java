package com.users.users_ms.infrastructure.adapters.client;


import com.users.users_ms.infrastructure.exceptions.RestaurantNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new RestaurantNotFoundException("El propietario no fue encontrado.");
        } else if (response.status() == 500) {
            return new RuntimeException("No existe un usuario con ese id.");
        }
        return defaultDecoder.decode(methodKey, response);
    }
}