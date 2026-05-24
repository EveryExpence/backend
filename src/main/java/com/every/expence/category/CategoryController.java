package com.every.expence.category;

import com.every.expence.category.dto.CategoryResponseDTO;
import com.every.expence.category.dto.CreateCategoryRequestDTO;
import com.every.expence.category.dto.UpdateCategoryRequestDTO;
import com.every.expence.user.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateCategoryRequestDTO createCategoryRequestDTO) {
        CategoryResponseDTO currCategory = categoryService.addCategory(user.getId(), createCategoryRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(currCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @AuthenticationPrincipal User user,
            @PathVariable("id") @NotNull String categoryId) {
        categoryService.deleteCategory(user.getId(), categoryId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public CategoryResponseDTO updateCategory(
            @AuthenticationPrincipal User user,
            @PathVariable("id") @NotNull String categoryId,
            @Valid @RequestBody UpdateCategoryRequestDTO updateCategoryRequestDTO) {
        return categoryService.updateCategory(user.getId(), categoryId, updateCategoryRequestDTO);
    }

    @GetMapping("/getAll")
    public List<CategoryResponseDTO> getCategories(
            @AuthenticationPrincipal User user) {
        String userId = user.getId();
        return categoryService.getByUserId(userId);
    }
}
