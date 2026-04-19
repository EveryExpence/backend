package com.every.expence.paymentMethod;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentMethodRepository extends MongoRepository<PaymentMethod, String> {
    Optional<PaymentMethod> findByIdAndUserId(String id, String userId);

    boolean existsByUserIdAndName(String userId, String name);

    long deleteByIdAndUserId(String id, String userId);

    Optional<PaymentMethod> findByUserIdAndName(String userId, String name);
}
