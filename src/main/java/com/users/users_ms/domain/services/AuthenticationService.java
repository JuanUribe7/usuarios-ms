package com.users.users_ms.domain.services;

import com.users.users_ms.commons.constants.ExceptionMessages;

import com.users.users_ms.commons.exceptions.NotFoundException;
import com.users.users_ms.commons.exceptions.UnauthorizedAccessException;
import com.users.users_ms.domain.model.LoginResponse;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.out.UserPersistencePort;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
public class AuthenticationService {

    private final UserPersistencePort userPort;



    public LoginResponse createLoginResponse(String token, String email, String role) {
        validateRole(role);
        return new LoginResponse(token, email, role);
    }

    public void validateRole(String role) {
        if (role == null || role.isEmpty()) {
            throw new UnauthorizedAccessException(ExceptionMessages.ROLE_CANNOT_BE_EMPTY);
        }
    }

    public User validateAndGetUser(String email) {
        return userPort.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }
}