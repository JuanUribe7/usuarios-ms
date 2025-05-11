package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.CreateOwnerRequestDto;

import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.OwnerService;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterOwnerServicePort;
import com.users.users_ms.domain.ports.in.ValidateServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final RegisterOwnerServicePort registerOwner;
    private final ValidateServicePort validateService;
    private final UserDtoMapper mapper;

    @Override
    public void saveOwner(CreateOwnerRequestDto dto) {
        User owner = mapper.toModel(dto);
        registerOwner.execute(owner);

    }

    @Override
    public boolean existsAndIsOwner(Long id) {
        return validateService.existsAndIsOwner(id);
    }
}




