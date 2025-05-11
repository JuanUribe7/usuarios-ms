package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.CreateEmployeeRequestDto;

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

    private final RegisterEmployeeServicePort registerEmployee;
    private final UserDtoMapper mapper;


    @Override
    public void saveEmployee(CreateEmployeeRequestDto dto) {
        User employee = mapper.toModel(dto);
        registerEmployee.saveEmployee(employee, dto.restaurantId());
    }
}