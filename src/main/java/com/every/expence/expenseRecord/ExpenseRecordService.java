package com.every.expence.expenseRecord;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.every.expence.account.Account;
import com.every.expence.account.AccountRepository;
import com.every.expence.expenseRecord.dto.CreateRequestDTO;
import com.every.expence.expenseRecord.dto.ExpenseRecordResponseDTO;
import com.every.expence.user.User;

@Service
public class ExpenseRecordService {
    private final ExpenseRecordRepository expenseRecordRepository;
    private final AccountRepository accountRepository;

    public ExpenseRecordService(
            ExpenseRecordRepository expenseRecordRepository,
            AccountRepository accountRepository) {
        this.expenseRecordRepository = expenseRecordRepository;
        this.accountRepository = accountRepository;
    }

    public ExpenseRecordResponseDTO create(User user, CreateRequestDTO createRequestDTO) {
        Account account = accountRepository.findById(createRequestDTO.accountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (!account.getOwnerId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account does not belong to user");
        }

        ExpenseRecord expenseRecord = new ExpenseRecord();
        expenseRecord.setUserId(user.getId());
        expenseRecord.setAmount(createRequestDTO.amount());
        expenseRecord.setDate(createRequestDTO.date());
        expenseRecord.setTime(createRequestDTO.time());
        expenseRecord.setLocation(createRequestDTO.location());
        expenseRecord.setAccountId(account.getId());
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
        return expenseRecordRepository.findByUserIdAndDateGreaterThanEqualOrderByDateAscTimeAsc(user.getId(), date)
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