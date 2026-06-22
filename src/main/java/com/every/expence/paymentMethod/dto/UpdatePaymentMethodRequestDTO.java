package com.every.expence.paymentMethod.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePaymentMethodRequestDTO(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        String name,
        
        @NotBlank(message = "Icon is required")
        String icon) {
}