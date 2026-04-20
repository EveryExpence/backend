package com.every.expence.expenseRecord.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.every.expence.expenseRecord.ExpenseRecord;

public record ExpenseRecordResponseDTO(String id,
        BigDecimal amount,
        LocalDate date,
        LocalTime time,
        String location,
        String description,
        List<String> attachments) {
    public static ExpenseRecordResponseDTO fromEntity(ExpenseRecord expenseRecord) {
        return new ExpenseRecordResponseDTO(
                expenseRecord.getId(),
                expenseRecord.getAmount(),
                expenseRecord.getDate(),
                expenseRecord.getTime(),
                expenseRecord.getLocation(),
                expenseRecord.getDescription(),
                expenseRecord.getAttachments());
    }
}
