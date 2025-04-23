package com.users.users_ms.application.services.impl;


import com.users.users_ms.application.dto.request.UserRequestDto;
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
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserServicePort userServicePort,
                          UserDtoMapper mapper,
                           PasswordEncoder passwordEncoder) {
        this.userServicePort = userServicePort;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User saveOwner(UserRequestDto dto) {
        User owner = mapper.toModel(dto);
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        return userServicePort.saveOwner(owner);
    }

}




