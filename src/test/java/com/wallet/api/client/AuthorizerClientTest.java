package com.wallet.api.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.wallet.api.client.dto.AuthorizerResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(AuthorizerClient.class)
class AuthorizerClientTest {

    @Autowired private AuthorizerClient authorizerClient;

    @Autowired private MockRestServiceServer server;

    @Test
    @DisplayName("Deve retornar autorização com sucesso quando o serviço externo responder OK")
    void shouldReturnAuthorizationSuccessfully() {
        String jsonResponse =
                """
            {
              "status": "success",
              "data": {
                "authorization": true
              }
            }
            """;

        this.server
                .expect(requestTo("https://util.devi.tools/api/v2/authorize"))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        AuthorizerResponse response = authorizerClient.isAuthorized();

        assertThat(response).isNotNull();
        assertThat(response.data().authorization()).isTrue();
    }
}
