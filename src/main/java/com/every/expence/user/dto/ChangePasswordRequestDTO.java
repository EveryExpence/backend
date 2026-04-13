package com.every.expence.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequestDTO(@NotBlank(message = "Old password is required") String oldPassword,
        @NotBlank(message = "New password is required") @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$", message = "Password must contain at least one digit, one lowercase, and one uppercase letter") String newPassword) {

}
