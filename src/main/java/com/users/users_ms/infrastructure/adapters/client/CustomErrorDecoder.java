package com.users.users_ms.infrastructure.adapters.client;


import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.NotFoundException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new NotFoundException(ExceptionMessages.RESTAURANT_NOT_FOUND);
        } else if (response.status() == 500) {
            return new NotFoundException(ExceptionMessages.USER_NOT_FOUND);
        }
        return defaultDecoder.decode(methodKey, response);
    }
}