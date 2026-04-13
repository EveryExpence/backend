package com.every.expence.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePublicUsernameRequestDTO(
                @NotBlank(message = "Username is required") @Size(min = 3, max = 30) String newPublicUsername) {

}
