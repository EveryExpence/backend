package com.every.expence.expenseRecord.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CreateRequestDTO(
        BigDecimal amount,
        LocalDate date,
        LocalTime time,
        String location,
        String description,
        List<String> attachments) {

}
