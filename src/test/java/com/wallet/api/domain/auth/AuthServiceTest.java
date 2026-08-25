package com.wallet.api.domain.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wallet.api.domain.auth.dto.LoginRequest;
import com.wallet.api.domain.auth.dto.LoginResponse;
import com.wallet.api.infra.security.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtService jwtService;
    @InjectMocks private AuthService authService;

    @Test
    @DisplayName("Should authenticate user and return JWT token successfully")
    void shouldAuthenticateUserAndReturnJwtToken() {
        LoginRequest request = new LoginRequest("test@wallet.com", "password123");
        String expectedToken = "mocked-jwt-token";

        when(jwtService.generateToken(any(UserDetails.class))).thenReturn(expectedToken);

        LoginResponse response = authService.authenticate(request);

        assertThat(response).isNotNull();
        assertThat(response.token()).isEqualTo(expectedToken);
        verify(authenticationManager).authenticate(any());
    }
}
