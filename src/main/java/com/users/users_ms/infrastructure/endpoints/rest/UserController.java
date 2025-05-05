package com.users.users_ms.infrastructure.endpoints.rest;


import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.services.OwnerService;
import com.users.users_ms.commons.constants.EndpointPaths;
import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.constants.RoleConstants;
import com.users.users_ms.commons.exceptions.UnauthorizedAccessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "Operations related to user registration")
@RequiredArgsConstructor
@RestController
@RequestMapping(EndpointPaths.BASE_USER)
public class UserController {

    private final OwnerService ownerService;

    @Operation(summary = "Register Owner", description = "Creates a new user with role OWNER")
    @PostMapping(EndpointPaths.OWNER)
    public ResponseEntity<UserResponseDto> createOwner(
            @Valid @RequestBody UserRequestDto dto,
            @RequestHeader String role) {
        if (!RoleConstants.ADMIN.equals(role)) {
            throw new UnauthorizedAccessException(ExceptionMessages.INVALID_ROLE_ADMIN);
        }
        return  ResponseEntity.ok(ownerService.saveOwner(dto));
    }


}