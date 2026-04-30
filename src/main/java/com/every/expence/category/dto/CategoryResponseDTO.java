package com.every.expence.category.dto;

import com.every.expence.category.Category;

public record CategoryResponeDTO(
    String Id,
    String userId,
    String name,
    String type
) {
    public static CategoryResponeDTO fromEntity(Category category){
        return new CategoryResponeDTO(
            category.getId(),
            category.getUserId(),
            category.getName(),
            category.getType()
        );
    }
}
