package com.every.expence.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DeleteCategoryRequestDTO (
    @NotBlank(message = "ID is required")
    @Size(max = 100, message = "Category ID must be at most 100 characters")
    String categoryId
){
    
}
