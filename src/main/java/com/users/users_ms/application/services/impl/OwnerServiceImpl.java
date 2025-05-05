package com.users.users_ms.application.services.impl;


import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.OwnerService;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterOwnerServicePort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

    private final RegisterOwnerServicePort ownerServicePort;

    @Override
    public UserResponseDto saveOwner(UserRequestDto dto ) {
        User owner = UserDtoMapper.toModel(dto);
        User response= ownerServicePort.execute(owner);
        return UserDtoMapper.toDto(response);
    }



}




