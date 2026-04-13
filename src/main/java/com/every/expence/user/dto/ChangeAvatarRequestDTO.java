package com.every.expence.user.dto;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.Size;

public record ChangeAvatarRequestDTO(
                @URL(message = "Avatar URL must be a valid URL") @Size(max = 512, message = "Avatar URL is too long") String avatarUrl) {
}
