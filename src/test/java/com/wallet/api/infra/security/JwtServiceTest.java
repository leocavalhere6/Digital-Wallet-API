package com.wallet.api.infra.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;
    private final String secretKey =
            "c3VwZXItc2VjcmV0LWtleS1mb3Itand0LXRlc3RzLW11c3QtYmUtbG9uZy1lbm91Z2g=";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000L);
        userDetails = new User("user@example.com", "password", Collections.emptyList());
    }

    @Test
    @DisplayName("Deve gerar um token JWT válido para o usuário informado")
    void shouldGenerateValidToken() {
        String token = jwtService.generateToken(userDetails);

        assertThat(token).isNotNull().isNotEmpty();
        assertThat(jwtService.extractUsername(token)).isEqualTo("user@example.com");
    }

    @Test
    @DisplayName("Deve validar corretamente um token JWT válido")
    void shouldValidateTokenSuccessfully() {
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertThat(isValid).isTrue();
    }
}
