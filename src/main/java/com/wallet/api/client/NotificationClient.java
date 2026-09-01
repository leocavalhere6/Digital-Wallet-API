package com.wallet.api.client;

import com.wallet.api.client.dto.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class NotificationClient {

    private static final Logger log = LoggerFactory.getLogger(NotificationClient.class);

    private final RestClient restClient;
    private final String notificationUrl;

    public NotificationClient(
            RestClient.Builder builder,
            @Value("${client.notification.url:https://util.devi.tools/api/v1/notify}")
                    String notificationUrl) {
        this.restClient = builder.build();
        this.notificationUrl = notificationUrl;
    }

    public void sendNotification(String email, String message) {
        try {
            restClient
                    .post()
                    .uri(notificationUrl)
                    .body(new NotificationRequest(email, message))
                    .retrieve()
                    .toBodilessEntity();
            log.info("Notification sent successfully to {}", email);
        } catch (Exception e) {
            log.warn("Failed to send notification to {}: {}", email, e.getMessage());
        }
    }
}
