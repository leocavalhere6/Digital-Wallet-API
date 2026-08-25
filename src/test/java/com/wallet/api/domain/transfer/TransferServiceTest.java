package com.wallet.api.domain.transfer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.wallet.api.domain.transfer.dto.TransferRequest;
import com.wallet.api.domain.transfer.dto.TransferResponse;
import com.wallet.api.domain.wallet.Wallet;
import com.wallet.api.domain.wallet.WalletRepository;
import com.wallet.api.domain.wallet.WalletType;
import com.wallet.api.exception.InsufficientBalanceException;
import com.wallet.api.exception.TransferNotAllowedException;
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
class TransferServiceTest {

    @Mock private WalletRepository walletRepository;

    @InjectMocks private TransferService transferService;

    @Test
    @DisplayName("Should process transfer successfully between valid user wallets")
    void shouldTransferSuccessfully() {
        UUID payerId = UUID.randomUUID();
        UUID payeeId = UUID.randomUUID();

        Wallet payer =
                new Wallet(
                        "User Payer",
                        "111",
                        "payer@email.com",
                        "pass",
                        BigDecimal.valueOf(200),
                        WalletType.USER);
        Wallet payee =
                new Wallet(
                        "User Payee",
                        "222",
                        "payee@email.com",
                        "pass",
                        BigDecimal.valueOf(50),
                        WalletType.USER);

        when(walletRepository.findById(payerId)).thenReturn(Optional.of(payer));
        when(walletRepository.findById(payeeId)).thenReturn(Optional.of(payee));

        TransferRequest request = new TransferRequest(payerId, payeeId, BigDecimal.valueOf(100));
        TransferResponse response = transferService.transfer(request);

        assertThat(response).isNotNull();
        assertThat(payer.getBalance()).isEqualTo(BigDecimal.valueOf(100));
        assertThat(payee.getBalance()).isEqualTo(BigDecimal.valueOf(150));
    }

    @Test
    @DisplayName("Should throw exception when merchant tries to perform transfer")
    void shouldThrowExceptionWhenMerchantTransfers() {
        UUID payerId = UUID.randomUUID();
        UUID payeeId = UUID.randomUUID();

        Wallet merchantPayer =
                new Wallet(
                        "Merchant",
                        "111",
                        "m@email.com",
                        "pass",
                        BigDecimal.valueOf(500),
                        WalletType.MERCHANT);
        Wallet payee =
                new Wallet(
                        "User Payee",
                        "222",
                        "payee@email.com",
                        "pass",
                        BigDecimal.valueOf(50),
                        WalletType.USER);

        when(walletRepository.findById(payerId)).thenReturn(Optional.of(merchantPayer));
        when(walletRepository.findById(payeeId)).thenReturn(Optional.of(payee));

        TransferRequest request = new TransferRequest(payerId, payeeId, BigDecimal.valueOf(100));

        assertThatThrownBy(() -> transferService.transfer(request))
                .isInstanceOf(TransferNotAllowedException.class)
                .hasMessage("Merchant wallets cannot send transfers");
    }

    @Test
    @DisplayName("Should throw exception when payer balance is insufficient")
    void shouldThrowExceptionWhenInsufficientBalance() {
        UUID payerId = UUID.randomUUID();
        UUID payeeId = UUID.randomUUID();

        Wallet payer =
                new Wallet(
                        "User Payer",
                        "111",
                        "payer@email.com",
                        "pass",
                        BigDecimal.valueOf(30),
                        WalletType.USER);
        Wallet payee =
                new Wallet(
                        "User Payee",
                        "222",
                        "payee@email.com",
                        "pass",
                        BigDecimal.valueOf(50),
                        WalletType.USER);

        when(walletRepository.findById(payerId)).thenReturn(Optional.of(payer));
        when(walletRepository.findById(payeeId)).thenReturn(Optional.of(payee));

        TransferRequest request = new TransferRequest(payerId, payeeId, BigDecimal.valueOf(100));

        assertThatThrownBy(() -> transferService.transfer(request))
                .isInstanceOf(InsufficientBalanceException.class);
    }
}
