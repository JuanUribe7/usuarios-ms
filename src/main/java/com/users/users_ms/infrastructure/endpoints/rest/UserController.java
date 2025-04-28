package com.users.users_ms.infrastructure.endpoints.rest;


import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.EmployeeServiceHandler;
import com.users.users_ms.application.services.OwnerServiceHandler;
import com.users.users_ms.application.services.impl.ClientServiceHandler;
import com.users.users_ms.infrastructure.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "users", description = "Operaciones relacionadas con la gestión de usuarios")
@RestController
@RequestMapping("/users")
public class UserController {

    private final OwnerServiceHandler ownerServiceHandler;
    private final EmployeeServiceHandler employeeServiceHandler;
    private final ClientServiceHandler clientServiceHandler;
    private final UserDtoMapper mapper;
    private final JwtUtil jwtUtil;

    public UserController(OwnerServiceHandler ownerServiceHandler,
                            EmployeeServiceHandler employeeServiceHandler,
                            ClientServiceHandler clientServiceHandler,
                          @Qualifier("userDtoMapperImpl") UserDtoMapper mapper,
                          JwtUtil jwtUtil) {
        this.ownerServiceHandler = ownerServiceHandler;
        this.employeeServiceHandler = employeeServiceHandler;
        this.clientServiceHandler = clientServiceHandler;
        this.mapper = mapper;
        this.jwtUtil = jwtUtil;
    }



    @Operation(summary = "Crear un nuevo usuario OWNER", description = "Crea un usuario con rol OWNER")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/owner")
    public ResponseEntity<UserResponseDto> createOwner(
            @Valid @RequestBody UserRequestDto dto) {
        return  ResponseEntity.ok(ownerServiceHandler.saveOwner(dto));
    }


    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/employee")
    public ResponseEntity<UserResponseDto> createEmployee(
            @Valid @RequestBody UserRequestDto dto,
            HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);
        Long ownerId = jwtUtil.extractUserId(token);

        return ResponseEntity.ok(employeeServiceHandler.saveEmployee(dto, ownerId));
    }

    @PostMapping("/client")
    public ResponseEntity<UserResponseDto> createClient(
            @Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(clientServiceHandler.saveClient(dto));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{ownerId}/restaurant/{restaurantId}")
    public ResponseEntity<String> updateOwnerRestaurant(
            @PathVariable Long ownerId,
            @PathVariable Long restaurantId) {

        ownerServiceHandler.updateOwnerRestaurantId(ownerId, restaurantId);
        return ResponseEntity.ok("Restaurante asignado correctamente al propietario.");
    }

    @Operation(summary = "Obtener rol de un usuario", description = "Retorna el rol del usuario por su ID")
    @ApiResponse(responseCode = "200", description = "Rol obtenido exitosamente")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/rol")
    public ResponseEntity<String> getUserRoleById(@PathVariable Long id) {
        String role = ownerServiceHandler.getUserRoleById(id);
        return ResponseEntity.ok(role);

    }


}
