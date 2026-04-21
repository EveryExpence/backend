package com.every.expence.category;
import com.every.expence.category.dto.CategoryResponeDTO;
import com.every.expence.category.dto.CreateCategoryRequestDTO;
import com.every.expence.category.dto.DeleteCategoryRequestDTO;
import com.every.expence.category.dto.UpdateCategoryRequestDTO;
import com.every.expence.user.User;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponeDTO> createCategory(
        @AuthenticationPrincipal User user,
        @Valid @RequestBody CreateCategoryRequestDTO createCategoryRequestDTO
    ){
        CategoryResponeDTO currCategory = categoryService.addCategory(user.getId(), createCategoryRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(currCategory);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCategory(
        @AuthenticationPrincipal User user,
        @Valid @RequestBody DeleteCategoryRequestDTO deleteCategoryRequestDTO
    ){
        categoryService.deleteCategory(user.getId(), deleteCategoryRequestDTO);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public CategoryResponeDTO updateCategory(
        @AuthenticationPrincipal User user,
        @Valid @RequestBody UpdateCategoryRequestDTO updateCategoryRequestDTO
    ){
        return categoryService.updateCategory(user.getId(), updateCategoryRequestDTO);
    }


    @GetMapping
    public List<CategoryResponeDTO> getCategories(
        @AuthenticationPrincipal User user,
        @RequestParam(required = false) String type
    ) {
        String userId = user.getId();
        if(type == null || type.isBlank()) return categoryService.getByUserId(userId);

        return categoryService.getByUserIdAndType(userId, type);
    }
}
