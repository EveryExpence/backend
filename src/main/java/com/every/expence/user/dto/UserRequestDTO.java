package com.every.expence.user.dto;

import com.every.expence.user.User;

public record UserRequestDTO(String email, String password) {
    public User toEntity() {
        return new User(email, password);
    }
}
