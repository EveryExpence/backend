package com.every.expence.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record GetAfterRequestDTO(@NotBlank(message = "Date is required")
String Date) {

}
