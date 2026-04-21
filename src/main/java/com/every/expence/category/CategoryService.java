package com.every.expence.category;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;

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

    
}
