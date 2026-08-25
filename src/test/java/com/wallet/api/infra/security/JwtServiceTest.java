package com.wallet.api.infra.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class JwtServiceTest {

    private JwtService jwtService;
    private final String secretKey =
            "c3VwZXItc2VjcmV0LWtleS1mb3Itand0LXRlc3RzLW11c3QtYmUtbG9uZy1lbm91Z2g=";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000L);
    }

    @Test
    @DisplayName("Deve gerar um token JWT válido para o email informado")
    void shouldGenerateValidToken() {
        String email = "user@example.com";

        String token = jwtService.generateToken(email);

        assertThat(token).isNotNull().isNotEmpty();
        assertThat(jwtService.extractUsername(token)).isEqualTo(email);
    }

    @Test
    @DisplayName("Deve validar corretamente um token JWT válido")
    void shouldValidateTokenSuccessfully() {
        String email = "user@example.com";
        String token = jwtService.generateToken(email);

        boolean isValid = jwtService.isTokenValid(token, email);

        assertThat(isValid).isTrue();
    }
}
