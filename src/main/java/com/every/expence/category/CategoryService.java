package com.every.expence.category;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(String name, String type){
        Category category = new Category(name, type);
        return categoryRepository.save(category);
    }
}
