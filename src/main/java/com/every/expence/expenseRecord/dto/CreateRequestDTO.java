package com.every.expence.expenseRecord.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRequestDTO(
                @NotNull(message = "Amount is required")
                @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
                BigDecimal amount,

                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                @NotNull(message = "Date is required")
                LocalDate date,

                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
                @NotNull(message = "Time is required")
                LocalTime time,

                String location,

                @NotBlank(message = "AccountId is required")
                String accountId,

                String description,
                List<String> attachments) {

}
