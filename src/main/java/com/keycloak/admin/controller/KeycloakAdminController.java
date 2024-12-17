package com.keycloak.admin.controller;


import com.keycloak.admin.dto.*;
import com.keycloak.admin.enums.UserRoleEnum;
import com.keycloak.admin.service.KeycloakAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class KeycloakAdminController {

    private final KeycloakAdminService keycloakAdminService;

    @PostMapping("/login")
    public AppResponse<KeycloakLoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return new AppResponse<>(keycloakAdminService.login(request));
    }

    @GetMapping("/search")
    public AppResponse<List<KeycloakUser>> searchUsers(@RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        return new AppResponse<>(keycloakAdminService.searchUsers(keyword, page, size));
    }

    @GetMapping("/find")
    public AppResponse<UserRepresentation> getUserByEmail(@RequestParam String email) {
        return new AppResponse<>(keycloakAdminService.getUserByEmail(email));
    }

    @PostMapping
    public AppResponse<Void> createNewUser(@Valid @RequestBody UserRequest request) {
        keycloakAdminService.createNewUser(request);
        return new AppResponse<>(HttpStatus.CREATED);
    }

    @GetMapping("/filter")
    public AppResponse<List<UserRepresentation>> getUsersByRole(@RequestParam UserRoleEnum role) {
        return new AppResponse<>(keycloakAdminService.getUsersByRole(role));
    }


}
