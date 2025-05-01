package com.users.users_ms.application.services;

import com.users.users_ms.application.dto.request.CreateEmployeeRequestDto;
import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;

public interface EmployeeService {
    UserResponseDto saveEmployee(CreateEmployeeRequestDto dto);
}
