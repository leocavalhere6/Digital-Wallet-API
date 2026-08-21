package com.wallet.api.domain.transfer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
        @NotNull @Positive BigDecimal value, @NotNull UUID payer, @NotNull UUID payee) {}
