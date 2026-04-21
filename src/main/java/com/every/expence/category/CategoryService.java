package com.every.expence.category;

import java.util.List;

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
        categoryRepository.findByUserIdAndNameAndType(userId, name, type)
            .ifPresent(c -> {
                throw new RuntimeException("Category already exists");
            });

        Category category = new Category(userId, name, type);
        return categoryRepository.save(category);
    }
    
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
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
        Category currCategory = getById(userId, categoryId);

        if(categoryRepository.findByUserIdAndNameAndTypeAndId(userId, name, type, categoryId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
        }

        currCategory.setName(name);
        currCategory.setUserId(name);
        currCategory.setType(type);

        return categoryRepository.save(currCategory);
    }

    public void deleteCategory(String userId, String categoryId, String name, String type){
        Category currCategory = getById(userId, categoryId);
        categoryRepository.delete(currCategory);
    }

    
}
