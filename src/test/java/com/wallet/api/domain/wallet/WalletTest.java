package com.wallet.api.domain.wallet;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WalletTest {

    @Test
    @DisplayName("Should instantiate wallet correctly with all attributes")
    void shouldCreateWalletInstance() {
        var wallet =
                new Wallet(
                        "John Doe",
                        "12345678901",
                        "john@email.com",
                        "secret123",
                        new BigDecimal("100.00"),
                        WalletType.USER);

        assertThat(wallet.getFullName()).isEqualTo("John Doe");
        assertThat(wallet.getCpfCnpj()).isEqualTo("12345678901");
        assertThat(wallet.getEmail()).isEqualTo("john@email.com");
        assertThat(wallet.getPassword()).isEqualTo("secret123");
        assertThat(wallet.getBalance()).isEqualByComparingTo("100.00");
        assertThat(wallet.getWalletType()).isEqualTo(WalletType.USER);
    }

    @Test
    @DisplayName("Should update wallet balance via setter")
    void shouldUpdateBalance() {
        var wallet =
                new Wallet(
                        "John Doe",
                        "12345678901",
                        "john@email.com",
                        "secret123",
                        BigDecimal.ZERO,
                        WalletType.USER);

        wallet.setBalance(new BigDecimal("250.50"));

        assertThat(wallet.getBalance()).isEqualByComparingTo("250.50");
    }
}
