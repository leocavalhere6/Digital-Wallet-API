package com.wallet.api.client;

import com.wallet.api.client.dto.AuthorizerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AuthorizerClient {

    private final RestClient restClient;
    private final String authorizerUrl;

    public AuthorizerClient(
            RestClient restClient, @Value("${client.authorizer.url}") String authorizerUrl) {
        this.restClient = restClient;
        this.authorizerUrl = authorizerUrl;
    }

    public boolean isAuthorized() {
        try {
            AuthorizerResponse response =
                    restClient.get().uri(authorizerUrl).retrieve().body(AuthorizerResponse.class);

            return response != null && response.isAuthorized();
        } catch (Exception e) {
            return false;
        }
    }
}
