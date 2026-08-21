package com.wallet.api.domain.wallet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WalletRepositoryTest {

    @Autowired private WalletRepository walletRepository;

    @Test
    @DisplayName("Should successfully persist and find wallet by ID")
    void shouldSaveAndFindWallet() {
        var wallet =
                new Wallet(
                        "John Doe",
                        "11122233344",
                        "john@email.com",
                        "pass123",
                        new BigDecimal("50.00"),
                        WalletType.USER);

        var savedWallet = walletRepository.save(wallet);

        assertThat(savedWallet.getId()).isNotNull();

        Optional<Wallet> found = walletRepository.findById(savedWallet.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getFullName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Should throw exception when saving duplicate CPF or CNPJ")
    void shouldThrowExceptionWhenDuplicateCpfCnpj() {
        var w1 =
                new Wallet(
                        "User One",
                        "12345678901",
                        "user1@email.com",
                        "pass",
                        BigDecimal.ZERO,
                        WalletType.USER);
        var w2 =
                new Wallet(
                        "User Two",
                        "12345678901",
                        "user2@email.com",
                        "pass",
                        BigDecimal.ZERO,
                        WalletType.MERCHANT);

        walletRepository.saveAndFlush(w1);

        assertThatThrownBy(() -> walletRepository.saveAndFlush(w2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Should throw exception when saving duplicate email")
    void shouldThrowExceptionWhenDuplicateEmail() {
        var w1 =
                new Wallet(
                        "User One",
                        "11111111111",
                        "shared@email.com",
                        "pass",
                        BigDecimal.ZERO,
                        WalletType.USER);
        var w2 =
                new Wallet(
                        "User Two",
                        "22222222222",
                        "shared@email.com",
                        "pass",
                        BigDecimal.ZERO,
                        WalletType.MERCHANT);

        walletRepository.saveAndFlush(w1);

        assertThatThrownBy(() -> walletRepository.saveAndFlush(w2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
