package com.users.users_ms.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User roles in the system")
public enum Role {
    @Schema(description = "Administrator role")
    ADMIN,
    @Schema(description = "Owner role")
    OWNER,
    @Schema(description = "Employee role")
    EMPLOYEE,
    @Schema(description = "Client role")
    CLIENT;
}