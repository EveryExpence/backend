package com.every.expence.category;
import com.every.expence.user.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(
        @AuthenticationPrincipal User user,
        @RequestBody Category category
    ){
        Category currCategory = categoryService.addCategory(userIdFromPrincipal(user), category.getName(), category.getType());

        return ResponseEntity.status(HttpStatus.CREATED).body(currCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
        @AuthenticationPrincipal User user,
        @PathVariable String categoryId
    ){
        categoryService.deleteCategory(userIdFromPrincipal(user), categoryId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{categoryId}")
    public Category updateCategory(
        @AuthenticationPrincipal User user,
        @PathVariable String categoryId,
        @RequestBody Category category
    ){
        return categoryService.updateCategory(userIdFromPrincipal(user), categoryId, category.getName(), category.getType());
    }


    @GetMapping
    public List<Category> getCategories(
        @AuthenticationPrincipal User user,
        @RequestParam(required = false) String type
    ) {
        String userId = userIdFromPrincipal(user);
        if(type == null || type.isBlank()) return categoryService.getByUserId(userId);

        return categoryService.getByUserIdAndType(userId, type);
    }


    private String userIdFromPrincipal(User user){
        if(user == null || user.getId() == null || user.getId().isBlank()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        return user.getId();
    }
}
