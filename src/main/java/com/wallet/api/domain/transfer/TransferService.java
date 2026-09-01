package com.wallet.api.domain.transfer;

import com.wallet.api.client.AuthorizerClient;
import com.wallet.api.client.NotificationClient;
import com.wallet.api.domain.transfer.dto.TransferRequest;
import com.wallet.api.domain.transfer.dto.TransferResponse;
import com.wallet.api.domain.wallet.Wallet;
import com.wallet.api.domain.wallet.WalletRepository;
import com.wallet.api.exception.InsufficientBalanceException;
import com.wallet.api.exception.TransferNotAllowedException;
import com.wallet.api.exception.WalletNotFoundException;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {

    private final WalletRepository walletRepository;
    private final AuthorizerClient authorizerClient;
    private final NotificationClient notificationClient;

    public TransferService(
            WalletRepository walletRepository,
            AuthorizerClient authorizerClient,
            NotificationClient notificationClient) {
        this.walletRepository = walletRepository;
        this.authorizerClient = authorizerClient;
        this.notificationClient = notificationClient;
    }

    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        Wallet payer =
                walletRepository
                        .findById(request.payerId())
                        .orElseThrow(() -> new WalletNotFoundException("Payer wallet not found"));

        Wallet payee =
                walletRepository
                        .findById(request.payeeId())
                        .orElseThrow(() -> new WalletNotFoundException("Payee wallet not found"));
        if (!payer.canTransfer()) {
            throw new TransferNotAllowedException(
                    "Merchant wallets are not allowed to send transfers");
        }

        if (!payer.hasBalanceFor(request.value())) {
            throw new InsufficientBalanceException("Payer does not have sufficient balance");
        }

        if (!authorizerClient.isAuthorized()) {
            throw new TransferNotAllowedException("Transfer unauthorized by external service");
        }

        payer.debit(request.value());
        payee.credit(request.value());

        walletRepository.save(payer);
        walletRepository.save(payee);

        notificationClient.sendNotification(
                payee.getEmail(), "You received a transfer of " + request.value());

        return new TransferResponse(
                request.value(), payer.getId(), payee.getId(), LocalDateTime.now());
    }
}
