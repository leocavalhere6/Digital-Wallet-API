package com.wallet.api.domain.wallet.dto;

import com.wallet.api.domain.wallet.WalletType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreateWalletRequest(
        @NotBlank(message = "FullName is required") String fullName,
        @NotBlank(message = "CpfCnpj is required") String cpfCnpj,
        @NotBlank(message = "Email is required") @Email(message = "Invalid email format")
                String email,
        @NotBlank(message = "Password is required") String password,
        @NotNull(message = "WalletType is required") WalletType walletType,
        @NotNull(message = "Initial balance is required")
                @PositiveOrZero(message = "Initial balance cannot be negative")
                BigDecimal balance) {}
