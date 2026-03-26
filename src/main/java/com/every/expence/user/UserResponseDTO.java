package com.every.expence.user;

public record UserResponseDTO(String email) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(user.getEmail());
    }
}
