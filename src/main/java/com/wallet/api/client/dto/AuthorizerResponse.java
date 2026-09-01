package com.wallet.api.client.dto;

public record AuthorizerResponse(String status, AuthorizerData data) {
    public record AuthorizerData(boolean authorization) {}

    public boolean isAuthorized() {
        return "success".equalsIgnoreCase(status) && data != null && data.authorization();
    }
}
