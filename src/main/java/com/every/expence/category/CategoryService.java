package com.every.expence.category;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.every.expence.category.dto.CategoryResponseDTO;
import com.every.expence.category.dto.CreateCategoryRequestDTO;
import com.every.expence.category.dto.UpdateCategoryRequestDTO;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO addCategory(String userId, CreateCategoryRequestDTO categoryCreateRequestDTO) {
        String normalizedName = normalizeName(categoryCreateRequestDTO.name());
        String normalizedType = normalizeType(categoryCreateRequestDTO.type());
        String icon = categoryCreateRequestDTO.icon();

        categoryRepository.findByUserIdAndNameAndType(
                userId,
                normalizedName,
                normalizedType)
                .ifPresent(c -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
                });

        categoryRepository.findByUserIdIsNullAndNameAndType(normalizedName, normalizedType)
                .ifPresent(c -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
                });

        Category category = new Category(categoryCreateRequestDTO.id(), userId, normalizedName, normalizedType, icon);
        return CategoryResponseDTO.fromEntity(categoryRepository.save(category));
    }

    public List<CategoryResponseDTO> getByUserId(String userId) {
        return categoryRepository.findByUserIdOrUserIdIsNull(userId)
                .stream()
                .map(CategoryResponseDTO::fromEntity)
                .toList();
    }

    private Category getByUserAndId(String userId, String categoryId) {
        return categoryRepository.findByUserIdAndId(userId, categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    public CategoryResponseDTO updateCategory(String userId, String categoryId,
            UpdateCategoryRequestDTO UpdateCategoryRequestDTO) {
        String normalizedName = normalizeName(UpdateCategoryRequestDTO.name());
        String normalizedType = normalizeType(UpdateCategoryRequestDTO.type());
        String icon = UpdateCategoryRequestDTO.icon();

        Category currCategory = getByUserAndId(userId, categoryId);

        Optional<Category> duplicate = categoryRepository
                .findByUserIdAndNameAndType(userId, normalizedName, normalizedType)
                .filter(f -> !Objects.equals(f.getId(), categoryId));

        if (duplicate.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
        }

        categoryRepository.findByUserIdIsNullAndNameAndType(normalizedName, normalizedType)
                .ifPresent(c -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
                });

        currCategory.setName(normalizedName);
        currCategory.setType(normalizedType);
        currCategory.setIcon(icon);

        Category saved = categoryRepository.save(currCategory);

        return CategoryResponseDTO.fromEntity(saved);
    }

    public void deleteCategory(String userId, String categoryId) {
        Category currCategory = getByUserAndId(userId, categoryId);
        categoryRepository.delete(currCategory);
    }

    private String normalizeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name is required");
        }

        return name.trim();
    }

    private String normalizeType(String type) {
        if (type == null || type.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category type is required");
        }

        return type.trim();
    }
}
