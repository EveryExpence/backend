package com.every.expence.category;
import java.util.*;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import com.mongodb.DuplicateKeyException;

public class CategoryDefaultsInitializer implements ApplicationRunner {
    private final CategoryRepository categoryRepository;

    private static final List<Category> DEFAULT_CATEGORIES = List.of(
        new Category("Food", "EXPENSE"),
        new Category("Transport", "EXPENSE"),
        new Category("Housing", "EXPENSE"),
        new Category("Utilities", "EXPENSE"),
        new Category("Healthcare", "EXPENSE"),
        new Category("Education", "EXPENSE"),
        new Category("Entertainment", "EXPENSE"),
        new Category("Shopping", "EXPENSE"),
        new Category("Travel", "EXPENSE"),
        new Category("Personal Care", "EXPENSE"),
        new Category("Salary", "INCOME"),
        new Category("Freelance", "INCOME"),
        new Category("Business", "INCOME"),
        new Category("Investment", "INCOME"),
        new Category("Gifts", "INCOME"),
        new Category("Other Income", "INCOME")
    );

    public CategoryDefaultsInitializer(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(ApplicationArguments args){
        for(Category category : DEFAULT_CATEGORIES){
            try{
                categoryRepository.save(category);
            }catch(DuplicateKeyException ignored){}
        }
    }
}