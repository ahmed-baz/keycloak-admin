package com.keycloak.admin.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public record KeycloakLoginResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken,
        @JsonProperty("expires_in")
        int accessExpiresIn,
        @JsonProperty("refresh_expires_in")
        int refreshExpiresIn
) {

}


