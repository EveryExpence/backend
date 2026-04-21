package com.every.expence.expenseRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRecordRepository extends MongoRepository<ExpenseRecord, String> {
    List<ExpenseRecord> findByUserIdAndDateGreaterThanEqualOrderByDateAscTimeAsc(String userId, LocalDate date);

    Optional<ExpenseRecord> findByIdAndUserId(String id, String userId);

    void deleteByIdAndUserId(String id, String userId);

    List<ExpenseRecord> findAllByUserIdOrderByDateDescTimeDesc(String userId);
}
