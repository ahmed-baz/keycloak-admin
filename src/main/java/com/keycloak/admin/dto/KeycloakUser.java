package com.keycloak.admin.dto;

import com.keycloak.admin.enums.UserRoleEnum;

import java.util.Date;
import java.util.List;

public record KeycloakUser(
        String id,
        String firstName,
        String lastName,
        String email,
        String userName,
        List<UserRoleEnum> roles,
        Date createdAt
) {

}
