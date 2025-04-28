package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.EmployeeServiceHandler;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.IEmployeeServicePort;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceHandlerImpl implements EmployeeServiceHandler {

    private final IEmployeeServicePort employeeServicePort;
    private final UserDtoMapper mapper;

    public EmployeeServiceHandlerImpl(IEmployeeServicePort IEmployeeServicePort,
                                      UserDtoMapper mapper) {
        this.employeeServicePort = IEmployeeServicePort;
        this.mapper = mapper;
    }

    @Override
    public UserResponseDto saveEmployee(UserRequestDto dto, Long ownerId) {
        User employee = mapper.toModel(dto);
        User response= employeeServicePort.saveEmployee(employee, ownerId);
        return mapper.toResponseDto(response);
    }
}
