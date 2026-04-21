package com.every.expence.expenseRecord.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record GetAfterRequestDTO(
        @NotNull(message = "Date is required")
        LocalDate date) {
}
