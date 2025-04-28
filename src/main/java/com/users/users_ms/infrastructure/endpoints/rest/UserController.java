package com.users.users_ms.infrastructure.endpoints.rest;


import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "users", description = "Operaciones relacionadas con la gestión de usuarios")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserDtoMapper mapper;

    public UserController(UserService userService,
                          @Qualifier("userDtoMapperImpl") UserDtoMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }



    @Operation(summary = "Crear un nuevo usuario OWNER", description = "Crea un usuario con rol OWNER")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/owner")
    public ResponseEntity<UserResponseDto> createOwner(
            @Valid @RequestBody UserRequestDto dto) {
        return  ResponseEntity.ok(userService.saveOwner(dto));
    }

    @Operation(summary = "Obtener rol de un usuario", description = "Retorna el rol del usuario por su ID")
    @ApiResponse(responseCode = "200", description = "Rol obtenido exitosamente")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping("/{id}/rol")
    public ResponseEntity<String> getUserRoleById(@PathVariable Long id) {
        String role = userService.getUserRoleById(id);
        return ResponseEntity.ok(role);



    }

}
