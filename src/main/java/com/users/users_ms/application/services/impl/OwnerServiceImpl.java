package com.users.users_ms.application.services.impl;


import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;

import com.users.users_ms.application.services.OwnerService;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterOwnerServicePort;
import com.users.users_ms.domain.ports.in.ValidateServicePort;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final RegisterOwnerServicePort ownerServicePort;
    private final ValidateServicePort validateService;

    public OwnerServiceImpl(RegisterOwnerServicePort ownerServicePort, ValidateServicePort validateService) {
        this.ownerServicePort = ownerServicePort;
        this.validateService = validateService;
    }
    @Override
    public UserResponseDto saveOwner(UserRequestDto dto) {
        User owner = UserDtoMapper.toModel(dto);
        User response= ownerServicePort.saveOwner(owner);
        return UserDtoMapper.toDto(response);
    }
    @Override
    public boolean existsAndIsOwner(Long id) {
        return validateService.existsAndIsOwner(id);
    }



}




