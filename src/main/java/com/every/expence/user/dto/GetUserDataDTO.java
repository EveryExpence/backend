package com.every.expence.user.dto;

public record GetUserDataDTO(
    String email,
    String publicUsername,
    String avatarUrl
) {}
