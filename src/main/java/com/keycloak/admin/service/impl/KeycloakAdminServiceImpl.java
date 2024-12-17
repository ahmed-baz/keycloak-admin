package com.keycloak.admin.service.impl;

import com.keycloak.admin.dto.KeycloakLoginResponse;
import com.keycloak.admin.dto.KeycloakUser;
import com.keycloak.admin.dto.LoginRequest;
import com.keycloak.admin.dto.UserRequest;
import com.keycloak.admin.enums.UserRoleEnum;
import com.keycloak.admin.service.KeycloakAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class KeycloakAdminServiceImpl implements KeycloakAdminService {

    private final Keycloak keycloak;
    private final RestTemplate restTemplate;
    @Value("${tesla.oauth2.url}")
    private String serverUrl;
    @Value("${tesla.oauth2.client.name}")
    private String clientId;
    @Value("${tesla.oauth2.claim.id}")
    private String claimId;

    @Override
    public KeycloakLoginResponse login(LoginRequest loginRequest) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
            data.add("grant_type", "password");
            data.add("client_id", clientId);
            data.add("username", loginRequest.username());
            data.add("password", loginRequest.password());
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data, headers);
            ResponseEntity<KeycloakLoginResponse> response = restTemplate.postForEntity(serverUrl + "/realms/" + claimId + "/protocol/openid-connect/token", request, KeycloakLoginResponse.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public UserRepresentation getUserByEmail(String email) {
        return keycloak.realm(claimId)
                .users()
                .searchByEmail(email, true)
                .get(0);
    }

    @Override
    public List<UserRepresentation> getUsersByRole(UserRoleEnum role) {
        return keycloak.realm(claimId)
                .roles()
                .get(role.name())
                .getUserMembers();
    }

    @Override
    public List<KeycloakUser> searchUsers(String keyword, int page, int size) {
        List<UserRepresentation> users = keycloak.realm(claimId)
                .users()
                .search(keyword, page, size);

        return users.stream()
                .map(
                        user -> new KeycloakUser(
                                user.getId(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getEmail(),
                                user.getUsername(),
                                List.of(),
                                new Date(user.getCreatedTimestamp())))
                .toList();

    }

    @Override
    public void createNewUser(UserRequest user) {
        createUser(user, user.role());
    }

    private void createUser(UserRequest user, UserRoleEnum role) {

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(user.firstName());
        userRepresentation.setLastName(user.lastName());
        userRepresentation.setUsername(user.email());
        userRepresentation.setEmail(user.email());

        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType("password");
        credential.setTemporary(false);
        credential.setValue(user.password());
        userRepresentation.setCredentials(List.of(credential));

        keycloak.realm(claimId)
                .users()
                .create(userRepresentation);
    }
}
