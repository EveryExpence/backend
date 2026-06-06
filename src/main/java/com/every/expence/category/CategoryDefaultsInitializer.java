package com.every.expence.category;
import java.util.*;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.mongodb.DuplicateKeyException;

@Component
public class CategoryDefaultsInitializer implements ApplicationRunner {
    private final CategoryRepository categoryRepository;

    private static final List<Category> DEFAULT_CATEGORIES = List.of(
        new Category("cat_food", null, "Food", "EXPENSE"),
        new Category("cat_transport", null, "Transport", "EXPENSE"),
        new Category("cat_housing", null, "Housing", "EXPENSE"),
        new Category("cat_utilities", null, "Utilities", "EXPENSE"),
        new Category("cat_shopping", null, "Shopping", "EXPENSE"),
        new Category("cat_entertainment", null, "Entertainment", "EXPENSE"),
        new Category("cat_others_expense", null, "Others", "EXPENSE"),
        new Category("cat_salary", null, "Salary", "INCOME"),
        new Category("cat_business", null, "Business", "INCOME"),
        new Category("cat_investment", null, "Investment", "INCOME"),
        new Category("cat_gifts", null, "Gifts", "INCOME"),
        new Category("cat_others_income", null, "Others", "INCOME")
    );

    public CategoryDefaultsInitializer(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(ApplicationArguments args){
        for(Category category : DEFAULT_CATEGORIES){
            try{
                boolean exists = categoryRepository.existsByUserIdIsNullAndNameAndType(category.getName(), category.getType());
                if(!exists) categoryRepository.save(category);
            }catch(DuplicateKeyException ignored) {}
        }
    }
}