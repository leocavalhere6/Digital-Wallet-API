package com.wallet.api.domain.wallet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.wallet.api.domain.wallet.dto.CreateWalletRequest;
import com.wallet.api.domain.wallet.dto.WalletResponse;
import com.wallet.api.exception.CpfCnpjAlreadyExistsException;
import com.wallet.api.exception.WalletNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock private WalletRepository walletRepository;

    @InjectMocks private WalletService walletService;

    @Test
    @DisplayName("Should create wallet successfully")
    void shouldCreateWalletSuccessfully() {
        CreateWalletRequest request =
                new CreateWalletRequest(
                        "Maria Silva",
                        "12345678900",
                        "maria@email.com",
                        "pass123",
                        WalletType.USER,
                        BigDecimal.valueOf(100));

        Wallet wallet =
                new Wallet(
                        "Maria Silva",
                        "12345678900",
                        "maria@email.com",
                        "pass123",
                        BigDecimal.valueOf(100),
                        WalletType.USER);

        when(walletRepository.existsByCpfCnpj(request.cpfCnpj())).thenReturn(false);
        when(walletRepository.existsByEmail(request.email())).thenReturn(false);
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        WalletResponse response = walletService.createWallet(request);

        assertThat(response).isNotNull();
        assertThat(response.email()).isEqualTo("maria@email.com");
    }

    @Test
    @DisplayName("Should throw exception when CPF/CNPJ already exists")
    void shouldThrowExceptionWhenCpfCnpjExists() {
        CreateWalletRequest request =
                new CreateWalletRequest(
                        "Maria Silva",
                        "12345678900",
                        "maria@email.com",
                        "pass123",
                        WalletType.USER,
                        BigDecimal.valueOf(100));

        when(walletRepository.existsByCpfCnpj(request.cpfCnpj())).thenReturn(true);

        assertThatThrownBy(() -> walletService.createWallet(request))
                .isInstanceOf(CpfCnpjAlreadyExistsException.class);
    }

    @Test
    @DisplayName("Should throw exception when finding non-existing wallet")
    void shouldThrowExceptionWhenWalletNotFound() {
        UUID id = UUID.randomUUID();
        when(walletRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> walletService.findWalletById(id))
                .isInstanceOf(WalletNotFoundException.class);
    }
}
