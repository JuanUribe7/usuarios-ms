package com.users.users_ms.application.dto.request;

import com.users.users_ms.commons.validation.ValidAge.ValidAge;
import com.users.users_ms.commons.validation.document.ValidIdentityDocument;
import com.users.users_ms.commons.validation.email.ValidEmail;
import com.users.users_ms.commons.validation.name.ValidName;
import com.users.users_ms.commons.validation.password.ValidPassword;
import com.users.users_ms.commons.validation.phone.ValidPhone;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
@Schema(description = "DTO para la creación de usuarios")
public class UserRequestDto {

    @Schema(description = "Nombre del usuario", example = "Juan", required = true)
    @ValidName
    private String name;

    @Schema(description = "Apellido del usuario", example = "Pérez", required = true)
    @ValidName
    private String lastName;

    @Schema(description = "Documento de identidad", example = "12345678", required = true)
    @ValidIdentityDocument
    private String identityDocument;
    @Schema(description = "Teléfono del usuario", example = "+573005698325", required = true)
    @ValidPhone
    private String phone;

    @Schema(description = "Fecha de nacimiento", example = "1990-01-01", required = true)
    @ValidAge
    private LocalDate birthDate;
    @Schema(description = "Correo electrónico del usuario", example = "juan@example.com", required = true)
    @ValidEmail
    private String email;
    @Schema(description = "Contraseña segura", example = "ClaveSegura123!", required = true)
    @ValidPassword
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
