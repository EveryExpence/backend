package com.every.expence.user.dto;

import jakarta.validation.constraints.Size;

public record ChangeAvatarRequestDTO(
        @Size(max = 512, message = "Avatar URL is too long")
        String avatarUrl) {
}
