package com.every.expence.category;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(String userId, String name, String type){
        categoryRepository.findByUserIdAndNameAndType(userId, normalizeName(name), normalizeType(type))
            .ifPresent(c -> {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
            });

        Category category = new Category(userId, normalizeName(name), type);

        return categoryRepository.save(category);
    }

    public List<Category> getByUserId(String userId){
        return categoryRepository.findByUserId(userId);
    }

    public List<Category> getByUserIdAndType(String userId, String type){
        return categoryRepository.findByUserIdAndType(userId, type);
    }

    public Category getById(String userId, String categoryId){
        return categoryRepository.findByUserIdAndId(userId, categoryId).
            orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }
    

    public Category updateCategory(String userId, String categoryId, String name, String type){
        String normalizedName = normalizeName(name);
        String normalizedType = normalizeType(type);

        Category currCategory = getById(userId, categoryId);

        Optional<Category> duplicate = 
            categoryRepository.findByUserIdAndNameAndType(userId, normalizedName, normalizedType)
            .filter(f -> !Objects.equals(f.getId(), categoryId));

        if(duplicate.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
        }

        currCategory.setName(normalizeName(normalizedName));
        currCategory.setType(normalizeType(normalizedType));

        return categoryRepository.save(currCategory);
    }

    public void deleteCategory(String userId, String categoryId){
        Category currCategory = getById(userId, categoryId);
        categoryRepository.delete(currCategory);
    }

    private String normalizeName(String name){
        if(name == null || name.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name is required");

        return name.trim();
    }

    private String normalizeType(String type){
        if(type == null || type.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category type is required");

        return type.trim();
    }
}
