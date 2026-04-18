package com.every.expence.account.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountResponseDTO(
    String id,
    String name,
    String currency,
    BigDecimal balance,
    Instant createdAt
) {
}