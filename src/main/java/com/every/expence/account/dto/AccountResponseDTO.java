package com.every.expence.account.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.every.expence.account.Account;

public record AccountResponseDTO(
    String id,
    String name,
    String currency,
    BigDecimal balance,
    Instant createdAt
) {
    public static AccountResponseDTO fromEntity(Account account) {
        return new AccountResponseDTO(
            account.getId(),
            account.getName(),
            account.getCurrency(),
            account.getBalance(),
            account.getCreatedAt()
        );
    }
}
