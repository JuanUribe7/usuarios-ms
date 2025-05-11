package com.users.users_ms.application.services;

import com.users.users_ms.application.dto.request.CreateClientRequestDto;

public interface ClientService {
    void saveClient(CreateClientRequestDto dto);

    String getPhoneByUserId(Long id);
}
