package com.users.users_ms.infrastructure.endpoints.rest;

import com.users.users_ms.application.dto.request.UserRequestDto;
import com.users.users_ms.application.dto.response.UserResponseDto;
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


    private final UserService userService;



    @GetMapping("/test-error")
    public ResponseEntity<String> throwError() {
        throw new RuntimeException("Error intencional para prueba de CloudWatch");
    }




    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("System is up and running");
    }

    @Operation(summary = "List All Users", description = "Returns all users")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto request) {
        return ResponseEntity.ok(userService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
