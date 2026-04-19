package com.every.expence.category;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findByUserId(String userId);
    List<Category> findByUserIdAndType(String userId, String Type);
    Optional<Category> findByUserIdAndNameAndType(String userId, String name, String type);
}