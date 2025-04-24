package com.users.users_ms.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
@Schema(description = "DTO para la creación de usuarios")
public class UserRequestDto {

    @Schema(description = "Nombre del usuario", example = "Juan", required = true)
    @NotNull(message = "El nombre no puede estar vacío")
    private String name;

    @Schema(description = "Apellido del usuario", example = "Pérez", required = true)

    private String lastName;

    @Schema(description = "Documento de identidad", example = "12345678", required = true)
    private String identityDocument;
    @Schema(description = "Teléfono del usuario", example = "+573005698325", required = true)
    private String phone;

    @Schema(description = "Fecha de nacimiento", example = "1990-01-01", required = true)
    private LocalDate birthDate;
    @Schema(description = "Correo electrónico del usuario", example = "juan@example.com", required = true)
    private String email;
    @Schema(description = "Contraseña segura", example = "ClaveSegura123!", required = true)
    private String password;


    public UserRequestDto() {
    }
    public UserRequestDto(String name, String lastName, String identityDocument, String phone, LocalDate birthDate, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.identityDocument = identityDocument;
        this.phone = phone;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(String identityDocument) {
        this.identityDocument = identityDocument;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
