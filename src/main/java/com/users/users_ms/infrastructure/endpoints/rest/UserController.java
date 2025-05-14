package com.users.users_ms.infrastructure.endpoints.rest;

import com.users.users_ms.application.dto.request.CreateClientRequestDto;
import com.users.users_ms.application.dto.request.CreateEmployeeRequestDto;
import com.users.users_ms.application.dto.request.CreateOwnerRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.services.EmployeeService;
import com.users.users_ms.application.services.OwnerService;
import com.users.users_ms.application.services.ClientService;
import com.users.users_ms.application.services.UserService;
import com.users.users_ms.commons.constants.ResponseMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Operations related to user registration")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final OwnerService ownerService;
    private final EmployeeService employeeService;
    private final ClientService clientService;
    private final UserService userService;



    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("System is up and running");
    }

    @Operation(summary = "List All Users", description = "Returns all users")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @Operation(summary = "Register Owner", description = "Creates a new user with role OWNER")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/owner")
    @ResponseStatus(HttpStatus.CREATED)
    public String createOwner(
            @Valid @RequestBody CreateOwnerRequestDto dto)
             {
        ownerService.saveOwner(dto);
        return ResponseMessages.OWNER_CREATED;
    }

    @Operation(summary = "Register Employee", description = "Creates a new user with role EMPLOYEE")
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public String createEmployee(
            @Valid @RequestBody CreateEmployeeRequestDto dto) {
        employeeService.saveEmployee(dto);
        return ResponseMessages.EMPLOYEE_CREATED;
    }

    @Operation(summary = "Register Client", description = "Creates a new user with role CLIENT")
    @PostMapping("/client")
    @ResponseStatus(HttpStatus.CREATED)
    public String createClient(
            @Valid @RequestBody CreateClientRequestDto dto) {
        clientService.saveClient(dto);
        return ResponseMessages.CLIENT_CREATED;
    }

    @GetMapping("/{id}/phone")
    public ResponseEntity<String> getPhoneByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getPhoneByUserId(id));
    }


    @Operation(summary = "Obtener rol de un usuario", description = "Retorna el rol del usuario por su ID")
    @ApiResponse(responseCode = "200", description = "Rol obtenido exitosamente")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsAndIsOwner(@PathVariable Long id) {
        boolean exists =ownerService.existsAndIsOwner(id);
        return ResponseEntity.ok(exists);
    }



}
