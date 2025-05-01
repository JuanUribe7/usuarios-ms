package com.users.users_ms.infrastructure.endpoints.rest;

import com.users.users_ms.application.dto.request.CreateEmployeeRequestDto;
import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.EmployeeService;
import com.users.users_ms.application.services.OwnerService;
import com.users.users_ms.application.services.ClientService;
import com.users.users_ms.infrastructure.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "users", description = "Operaciones relacionadas con la gestión de usuarios")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final OwnerService ownerService;
    private final EmployeeService employeeService;
    private final ClientService clientService;

    @Operation(summary = "Crear un nuevo usuario OWNER", description = "Crea un usuario con rol OWNER")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/owner")
    public ResponseEntity<UserResponseDto> createOwner(
            @Valid @RequestBody UserRequestDto dto) {
        return  ResponseEntity.ok(ownerService.saveOwner(dto));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/employee")
    public ResponseEntity<UserResponseDto> createEmployee(
            @Valid @RequestBody CreateEmployeeRequestDto dto) {
        return ResponseEntity.ok(employeeService.saveEmployee(dto));
    }

    @PostMapping("/client")
    public ResponseEntity<UserResponseDto> createClient(
            @Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(clientService.saveClient(dto));
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
