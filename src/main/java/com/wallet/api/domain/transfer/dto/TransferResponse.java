package com.wallet.api.domain.transfer.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferResponse(BigDecimal value, UUID payer, UUID payee, LocalDateTime timestamp) {}
