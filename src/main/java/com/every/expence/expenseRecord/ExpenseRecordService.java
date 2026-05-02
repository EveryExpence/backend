package com.every.expence.expenseRecord;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.every.expence.account.Account;
import com.every.expence.account.AccountRepository;
import com.every.expence.category.Category;
import com.every.expence.category.CategoryRepository;
import com.every.expence.expenseRecord.dto.CreateRequestDTO;
import com.every.expence.expenseRecord.dto.ExpenseRecordResponseDTO;
import com.every.expence.paymentMethod.PaymentMethod;
import com.every.expence.paymentMethod.PaymentMethodRepository;
import com.every.expence.user.User;

@Service
public class ExpenseRecordService {
    private final ExpenseRecordRepository expenseRecordRepository;
    private final AccountRepository accountRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseRecordService(
            ExpenseRecordRepository expenseRecordRepository,
            AccountRepository accountRepository,
            PaymentMethodRepository paymentMethodRepository,
            CategoryRepository categoryRepository) {
        this.expenseRecordRepository = expenseRecordRepository;
        this.accountRepository = accountRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.categoryRepository = categoryRepository;
    }

    public ExpenseRecordResponseDTO create(User user, CreateRequestDTO createRequestDTO) {
        Account account = accountRepository.findById(createRequestDTO.accountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (!account.getOwnerId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account does not belong to user");
        }

        PaymentMethod paymentMethod = paymentMethodRepository.findByIdAndUserId(createRequestDTO.paymentMethodId(), user.getId())
            .or(() -> paymentMethodRepository.findByIdAndUserIdIsNull(createRequestDTO.paymentMethodId()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment method not found"));

        Category category = categoryRepository.findByUserIdAndId(user.getId(), createRequestDTO.categoryId())
            .or(() -> categoryRepository.findByIdAndUserIdIsNull(createRequestDTO.categoryId()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        ExpenseRecord expenseRecord = new ExpenseRecord();
        expenseRecord.setUserId(user.getId());
        expenseRecord.setAmount(createRequestDTO.amount());
        expenseRecord.setDate(createRequestDTO.date());
        expenseRecord.setTime(createRequestDTO.time());
        expenseRecord.setLocation(createRequestDTO.location());
        expenseRecord.setAccountId(account.getId());
        expenseRecord.setPaymentMethodId(paymentMethod.getId());
        expenseRecord.setCategoryId(category.getId());
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