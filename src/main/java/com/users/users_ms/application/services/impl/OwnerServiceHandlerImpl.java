package com.users.users_ms.application.services.impl;


import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;

import com.users.users_ms.application.services.OwnerServiceHandler;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.IOwnerServicePort;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceHandlerImpl implements OwnerServiceHandler {

    private final IOwnerServicePort ownerServicePort;
    private final UserDtoMapper mapper;



    public OwnerServiceHandlerImpl(IOwnerServicePort ownerServicePort,
                                   UserDtoMapper mapper) {
        this.ownerServicePort = ownerServicePort;
        this.mapper = mapper;

    }


    @Override
    public UserResponseDto saveOwner(UserRequestDto dto) {
        User owner = mapper.toModel(dto);
        User response= ownerServicePort.saveOwner(owner);
        return mapper.toResponseDto(response);
    }




    @Override
    public String getUserRoleById(Long id) {
        User user = ownerServicePort.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return user.getRole().name(); // Asumiendo que getRole() devuelve un enum Role
    }

    @Override
    public void updateOwnerRestaurantId(Long ownerId, Long restaurantId) {
        ownerServicePort.updateOwnerRestaurantId(ownerId, restaurantId);
    }

}




