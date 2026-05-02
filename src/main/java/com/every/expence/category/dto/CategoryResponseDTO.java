package com.every.expence.category.dto;

import com.every.expence.category.Category;

public record CategoryResponseDTO(
    String Id,
    String userId,
    String name,
    String type
) {
    public static CategoryResponseDTO fromEntity(Category category){
        return new CategoryResponseDTO(
            category.getId(),
            category.getUserId(),
            category.getName(),
            category.getType()
        );
    }
}
