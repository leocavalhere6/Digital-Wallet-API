package com.wallet.api.domain.transfer;

import com.wallet.api.domain.transfer.dto.TransferRequest;
import com.wallet.api.domain.transfer.dto.TransferResponse;
import com.wallet.api.domain.wallet.Wallet;
import com.wallet.api.domain.wallet.WalletRepository;
import com.wallet.api.domain.wallet.WalletType;
import com.wallet.api.exception.InsufficientBalanceException;
import com.wallet.api.exception.TransferNotAllowedException;
import com.wallet.api.exception.WalletNotFoundException;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {

    private final WalletRepository walletRepository;

    public TransferService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        if (request.payer().equals(request.payee())) {
            throw new TransferNotAllowedException("Cannot transfer to the same wallet");
        }

        Wallet payer =
                walletRepository
                        .findById(request.payer())
                        .orElseThrow(() -> new WalletNotFoundException(request.payer()));

        Wallet payee =
                walletRepository
                        .findById(request.payee())
                        .orElseThrow(() -> new WalletNotFoundException(request.payee()));

        validateTransfer(payer, request);

        payer.setBalance(payer.getBalance().subtract(request.value()));
        payee.setBalance(payee.getBalance().add(request.value()));

        walletRepository.save(payer);
        walletRepository.save(payee);

        return new TransferResponse(
                request.value(), payer.getId(), payee.getId(), LocalDateTime.now());
    }

    private void validateTransfer(Wallet payer, TransferRequest request) {
        if (payer.getWalletType() == WalletType.MERCHANT) {
            throw new TransferNotAllowedException("Merchant wallets cannot send transfers");
        }

        if (payer.getBalance().compareTo(request.value()) < 0) {
            throw new InsufficientBalanceException();
        }
    }
}
