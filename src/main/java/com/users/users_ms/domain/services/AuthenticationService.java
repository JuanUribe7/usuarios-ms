package com.users.users_ms.domain.services;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.NotFoundException;
import com.users.users_ms.commons.exceptions.UnauthorizedAccessException;
import com.users.users_ms.domain.model.LoginResponse;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.model.UserDetailsDomain;
import com.users.users_ms.domain.ports.out.TokenProviderPort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;

public class AuthenticationService {

    private final UserPersistencePort userPort;
    private final TokenProviderPort tokenProvider;

    public AuthenticationService(UserPersistencePort userPort, TokenProviderPort tokenProvider) {
        this.userPort = userPort;
        this.tokenProvider = tokenProvider;
    }

    public LoginResponse authenticateUser(String email, UserDetailsDomain userDetails) {
        User user = validateAndGetUser(email);
        String token = tokenProvider.generateToken(userDetails, user.getId(), user.getRole().name());
        return createLoginResponse(token, user.getEmail(), user.getRole().name());
    }

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