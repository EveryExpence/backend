package com.every.expence.expenseRecord;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.every.expence.expenseRecord.dto.CreateRequestDTO;
import com.every.expence.expenseRecord.dto.ExpenseRecordResponseDTO;
import com.every.expence.user.User;

@Service
public class ExpenseRecordService {
    private final ExpenseRecordRepository expenseRecordRepository;

    public ExpenseRecordService(ExpenseRecordRepository expenseRecordRepository) {
        this.expenseRecordRepository = expenseRecordRepository;
    }

    public ExpenseRecordResponseDTO create(User user, CreateRequestDTO createRequestDTO) {
        ExpenseRecord expenseRecord = new ExpenseRecord();
        expenseRecord.setUserId(user.getId());
        expenseRecord.setAmount(createRequestDTO.amount());
        expenseRecord.setDate(createRequestDTO.date());
        expenseRecord.setTime(createRequestDTO.time());
        expenseRecord.setLocation(createRequestDTO.location());
        expenseRecord.setDescription(createRequestDTO.description());
        expenseRecord.setAttachments(createRequestDTO.attachments());

        return ExpenseRecordResponseDTO.fromEntity(expenseRecordRepository.save(expenseRecord));
    }

    public ExpenseRecordResponseDTO getById(User user, String id) {
    return expenseRecordRepository.findByIdAndUserId(id, user.getId())
            .map(ExpenseRecordResponseDTO::fromEntity)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));
}

    public List<ExpenseRecordResponseDTO> getAll(User user) {
        return expenseRecordRepository.findAllByUserIdOrderByDateDescTimeDesc(user.getId())
                .stream()
                .map(ExpenseRecordResponseDTO::fromEntity)
                .toList();
    }

    public List<ExpenseRecordResponseDTO> getAfter(User user, LocalDate date) {
        return expenseRecordRepository.findByUserIdAndDateAfterOrderByDateAscTimeAsc(user.getId(), date)
                .stream()
                .map(ExpenseRecordResponseDTO::fromEntity)
                .toList();
    }

    public void deleteById(User user, String id) {
        ExpenseRecord expenseRecord = expenseRecordRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));

        expenseRecordRepository.delete(expenseRecord);
    }

}
