package com.keycloak.admin.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record LoginRequest(
        @NotNull(message = "username is required")
        @NotEmpty(message = "username is required")
        String username,
        @NotNull(message = "password is required")
        @NotEmpty(message = "password is required")
        String password
) {

}


