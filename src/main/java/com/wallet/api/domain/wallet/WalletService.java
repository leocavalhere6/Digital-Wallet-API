package com.wallet.api.domain.wallet;

import com.wallet.api.domain.wallet.dto.CreateWalletRequest;
import com.wallet.api.domain.wallet.dto.WalletResponse;
import com.wallet.api.exception.CpfCnpjAlreadyExistsException;
import com.wallet.api.exception.EmailAlreadyExistsException;
import com.wallet.api.exception.WalletNotFoundException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public WalletResponse createWallet(CreateWalletRequest request) {
        if (walletRepository.existsByCpfCnpj(request.cpfCnpj())) {
            throw new CpfCnpjAlreadyExistsException(request.cpfCnpj());
        }

        if (walletRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        Wallet wallet =
                new Wallet(
                        request.fullName(),
                        request.cpfCnpj(),
                        request.email(),
                        request.password(),
                        request.balance(),
                        request.walletType());

        Wallet savedWallet = walletRepository.save(wallet);
        return WalletResponse.fromEntity(savedWallet);
    }

    @Transactional(readOnly = true)
    public WalletResponse findWalletById(UUID id) {
        Wallet wallet =
                walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));
        return WalletResponse.fromEntity(wallet);
    }
}
