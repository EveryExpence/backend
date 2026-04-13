package com.every.expence.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangeEmailRequestDTO(
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email) {
}
