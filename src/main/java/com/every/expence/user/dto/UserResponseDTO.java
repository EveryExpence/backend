package com.every.expence.user.dto;

import com.every.expence.user.User;

public record UserResponseDTO(String email) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(user.getEmail());
    }
}
