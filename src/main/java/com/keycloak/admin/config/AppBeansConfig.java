package com.keycloak.admin.config;


import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class AppBeansConfig {

    @Value("${tesla.oauth2.url}")
    private String serverUrl;
    @Value("${tesla.oauth2.username}")
    private String username;
    @Value("${tesla.oauth2.password}")
    private String password;


    @Bean
    Keycloak keycloak() {
        ResteasyClient resteasyClient = new ResteasyClientBuilderImpl().connectionPoolSize(10).build();
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master")
                .clientId("admin-cli")
                .grantType(OAuth2Constants.PASSWORD)
                .username(username)
                .password(password)
                .resteasyClient(resteasyClient)
                .build();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
