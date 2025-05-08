package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.CreateEmployeeRequestDto;
import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.EmployeeService;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterEmployeeServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final RegisterEmployeeServicePort employeeServicePort;




    @Override
    public UserResponseDto saveEmployee(CreateEmployeeRequestDto dto) {
        User employee = UserDtoMapper.toModel(dto);
        User response= employeeServicePort.saveEmployee(employee, dto.getRestaurantId());
        return (UserDtoMapper.toDto(response));
    }
}
