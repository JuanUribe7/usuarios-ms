package com.users.users_ms.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeRequestDto {
    private String name;
    private String lastName;
    private String identityDocument;
    private String email;
    private String password;
    private String phone;
    private LocalDate birthDate;
    private Long restaurantId;
}
