package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.IClientServicePort;
import org.springframework.stereotype.Service;


@Service
public class ClientServiceHandlerImpl implements ClientServiceHandler {

    private final IClientServicePort clientServicePort;
    private final UserDtoMapper mapper;

    public ClientServiceHandlerImpl(IClientServicePort clientServicePort, UserDtoMapper mapper) {
        this.clientServicePort = clientServicePort;
        this.mapper = mapper;
    }

    @Override
    public UserResponseDto saveClient(UserRequestDto dto) {
        User client = mapper.toModel(dto);
        User response = clientServicePort.saveClient(client);
        return mapper.toResponseDto(response);
    }
}
