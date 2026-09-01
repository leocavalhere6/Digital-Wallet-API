package com.wallet.api.domain.transfer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
        @NotNull(message = "Payer ID is required") UUID payerId,
        @NotNull(message = "Payee ID is required") UUID payeeId,
        @NotNull(message = "Value is required")
                @Positive(message = "Transfer value must be greater than zero")
                BigDecimal value) {}
