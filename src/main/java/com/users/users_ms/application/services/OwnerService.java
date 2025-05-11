package com.users.users_ms.application.services;


import com.users.users_ms.application.dto.request.CreateOwnerRequestDto;

public interface OwnerService {
    void saveOwner(CreateOwnerRequestDto dto);
    boolean existsAndIsOwner(Long id);
}
