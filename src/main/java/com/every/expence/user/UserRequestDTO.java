package com.every.expence.user;

public record UserRequestDTO(String email, String password) {
    public User toEntity() {
        return new User(email, password);
    }
}
