package com.wallet.api.client;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.wallet.api.infra.config.RestClientConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(NotificationClient.class)
@Import(RestClientConfig.class)
class NotificationClientTest {

    @Autowired private NotificationClient notificationClient;
    @Autowired private MockRestServiceServer server;

    @Test
    @DisplayName("Deve disparar a notificação para o serviço externo com sucesso")
    void shouldSendNotificationSuccessfully() {
        this.server
                .expect(requestTo("https://util.devi.tools/api/v1/notify"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess());

        notificationClient.sendNotification("user@example.com", "Você recebeu uma transferência.");

        this.server.verify();
    }
}
