package com.wallet.api.domain.wallet.dto;

import com.wallet.api.domain.wallet.WalletType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreateWalletRequest(
        @NotBlank String fullName,
        @NotBlank String cpfCnpj,
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotNull @PositiveOrZero BigDecimal balance,
        @NotNull WalletType walletType) {}
