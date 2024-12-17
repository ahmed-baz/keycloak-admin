package com.keycloak.admin.dto;


import com.keycloak.admin.enums.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


public record UserRequest(
        @NotNull(message = "first name is required")
        String firstName,
        @NotNull(message = "last name is required")
        String lastName,
        @NotNull(message = "email is required")
        @Email(message = "email is not valid")
        String email,
        @NotNull(message = "password is required")
        String password,
        @NotNull(message = "role is required")
        UserRoleEnum role
) {
}
