package com.every.expence.category;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findByUserId(String userId);
    List<Category> findByUserIdAndType(String userId, String type);

    Optional<Category> findByUserIdAndId(String userId, String categoryId);
    Optional<Category> findByUserIdAndNameAndType(String userId, String name, String type);
    Optional<Category> findByUserIdIsNullAndNameAndType(String name, String type);
    boolean existsByUserIdIsNullAndNameAndType(String name, String type);

    @Query("{ '$or': [ { 'userId': ?0 }, { 'userId': null } ] }")
    List<Category> findAllVisibleForUser(String userId);

    @Query("{ '$and': [ { 'type': ?1 }, { '$or': [ { 'userId': ?0 }, { 'userId': null } ] } ] }")
    List<Category> findAllVisibleForUserByType(String userId, String type);
}