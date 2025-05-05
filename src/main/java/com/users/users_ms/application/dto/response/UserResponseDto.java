package com.users.users_ms.application.dto.response;

import com.users.users_ms.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String lastName;
    private String identityDocument;
    private String email;
    private Role role;


}