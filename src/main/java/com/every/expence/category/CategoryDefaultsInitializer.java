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
        new Category("cat_food", null, "Food", "EXPENSE", "food-fork-drink"),
        new Category("cat_transport", null, "Transport", "EXPENSE", "car"),
        new Category("cat_housing", null, "Housing", "EXPENSE", "home"),
        new Category("cat_utilities", null, "Utilities", "EXPENSE", "lightning-bolt"),
        new Category("cat_shopping", null, "Shopping", "EXPENSE", "cart"),
        new Category("cat_entertainment", null, "Entertainment", "EXPENSE", "gamepad-variant"),
        new Category("cat_others_expense", null, "Others", "EXPENSE", "dots-horizontal"),
        new Category("cat_salary", null, "Salary", "INCOME", "cash-multiple"),
        new Category("cat_business", null, "Business", "INCOME", "briefcase"),
        new Category("cat_investment", null, "Investment", "INCOME", "chart-line"),
        new Category("cat_gifts", null, "Gifts", "INCOME", "gift"),
        new Category("cat_others_income", null, "Others", "INCOME", "dots-horizontal")
    );

    public CategoryDefaultsInitializer(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(ApplicationArguments args){
        for(Category category : DEFAULT_CATEGORIES){
            try{
                if(!categoryRepository.existsById(category.getId())) {
                    categoryRepository.save(category);
                }
            }catch(DuplicateKeyException ignored) {}
        }
    }
}