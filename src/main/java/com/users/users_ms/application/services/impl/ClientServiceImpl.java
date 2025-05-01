package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.ClientService;
import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterClientServicePort;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final RegisterClientServicePort clientServicePort;


    public ClientServiceImpl(RegisterClientServicePort clientServicePort) {
        this.clientServicePort = clientServicePort;

    }

    @Override
    public UserResponseDto saveClient(UserRequestDto dto) {
        User client = UserDtoMapper.toModel(dto);
        User response = clientServicePort.saveClient(client);
        return UserDtoMapper.toDto(response);
    }
}
