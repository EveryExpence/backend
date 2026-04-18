package com.every.expence.account;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
    List<Account> findAllByOwnerId(String ownerId);

    Optional<Account> findByIdAndOwnerId(String id, String ownerId);
}