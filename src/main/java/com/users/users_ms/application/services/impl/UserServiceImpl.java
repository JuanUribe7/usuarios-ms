package com.users.users_ms.application.services.impl;


import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.UserService;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.UserServicePort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserServicePort userServicePort;
    private final UserDtoMapper mapper;



    public UserServiceImpl(UserServicePort userServicePort,
                          UserDtoMapper mapper) {
        this.userServicePort = userServicePort;
        this.mapper = mapper;

    }


    @Override
    public UserResponseDto saveOwner(UserRequestDto dto) {
        User owner = mapper.toModel(dto);

        User response= userServicePort.saveOwner(owner);
        return mapper.toResponseDto(response);
    }

    @Override
    public String getUserRoleById(Long id) {
        User user = userServicePort.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return user.getRole().name(); // Asumiendo que getRole() devuelve un enum Role
    }

}




