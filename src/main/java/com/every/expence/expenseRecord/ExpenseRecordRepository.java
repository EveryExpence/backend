package com.every.expence.expenseRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRecordRepository extends MongoRepository<ExpenseRecord, String> {
    List<ExpenseRecord> findByUserIdAndDateAfterOrderByDateAscTimeAsc(String userId, LocalDate date);

    Optional<ExpenseRecord> findByIdAndUserId(String id, String UserId);

    void deleteByIdAndUserId(String id, String userId);

    List<ExpenseRecord> findAllByUserIdOrderByDateDescTimeDesc(String userId);
}
