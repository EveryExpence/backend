package com.every.expence.expenseRecord.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.every.expence.expenseRecord.ExpenseRecord;
import com.fasterxml.jackson.annotation.JsonFormat;

public record ExpenseRecordResponseDTO(String id,
        BigDecimal amount,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        LocalTime time,
        String location,
        String accountId,
        String paymentMethodId,
        String categoryId,
        String description,
        List<String> attachments) {
    public static ExpenseRecordResponseDTO fromEntity(ExpenseRecord expenseRecord) {
        return new ExpenseRecordResponseDTO(
                expenseRecord.getId(),
                expenseRecord.getAmount(),
                expenseRecord.getDate(),
                expenseRecord.getTime(),
                expenseRecord.getLocation(),
                expenseRecord.getAccountId(),
                expenseRecord.getPaymentMethodId(),
                expenseRecord.getCategoryId(),
                expenseRecord.getDescription(),
                expenseRecord.getAttachments());
    }
}
