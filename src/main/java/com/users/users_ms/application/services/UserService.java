package com.users.users_ms.application.services;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.domain.model.User;

public interface UserService {
    User saveOwner(UserRequestDto dto);
}
