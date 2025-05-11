package com.users.users_ms.application.services;

import com.users.users_ms.application.dto.request.CreateEmployeeRequestDto;

public interface EmployeeService {
    void saveEmployee(CreateEmployeeRequestDto dto);
}
