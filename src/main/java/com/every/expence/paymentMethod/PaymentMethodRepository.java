package com.every.expence.paymentMethod;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentMethodRepository extends MongoRepository<PaymentMethod, String> {
    Optional<PaymentMethod> findByIdAndUserId(String id, String userId);

    boolean existsByUserIdAndName(String userId, String name);

    long deleteByIdAndUserId(String id, String userId);

    List<PaymentMethod> findByUserIdOrUserIdIsNullOrderByNameAsc(String userId);

    boolean existsByUserIdIsNullAndName(String name);

    Optional<PaymentMethod> findByUserIdIsNullAndName(String name);

}
