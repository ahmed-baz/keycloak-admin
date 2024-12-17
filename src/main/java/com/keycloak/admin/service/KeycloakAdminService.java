package com.keycloak.admin.service;

import com.keycloak.admin.dto.KeycloakLoginResponse;
import com.keycloak.admin.dto.KeycloakUser;
import com.keycloak.admin.dto.LoginRequest;
import com.keycloak.admin.dto.UserRequest;
import com.keycloak.admin.enums.UserRoleEnum;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeycloakAdminService {

    KeycloakLoginResponse login(LoginRequest loginRequest);

    UserRepresentation getUserByEmail(String email);

    List<UserRepresentation> getUsersByRole(UserRoleEnum role);

    List<KeycloakUser> searchUsers(String keyword, int page, int size);

    void createNewUser(UserRequest user);

}
