package com.wallet.api.domain.wallet.dto;

import com.wallet.api.domain.wallet.Wallet;
import com.wallet.api.domain.wallet.WalletType;
import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponse(
        UUID id,
        String fullName,
        String cpfCnpj,
        String email,
        BigDecimal balance,
        WalletType walletType) {
    public static WalletResponse fromEntity(Wallet wallet) {
        return new WalletResponse(
                wallet.getId(),
                wallet.getFullName(),
                wallet.getCpfCnpj(),
                wallet.getEmail(),
                wallet.getBalance(),
                wallet.getWalletType());
    }
}
