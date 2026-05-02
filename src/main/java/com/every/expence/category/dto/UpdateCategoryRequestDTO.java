package com.every.expence.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequestDTO (
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    String name,

    @NotBlank(message = "Type is required")
    @Size(max = 100, message = "Type must be at most 100 characters")
    String type
){
}
