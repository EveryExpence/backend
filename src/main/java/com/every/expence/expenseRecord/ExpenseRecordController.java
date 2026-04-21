package com.every.expence.expenseRecord;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.every.expence.expenseRecord.dto.GetAfterRequestDTO;
import com.every.expence.expenseRecord.dto.CreateRequestDTO;
import com.every.expence.expenseRecord.dto.ExpenseRecordResponseDTO;
import com.every.expence.user.User;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/expenseRecord")
public class ExpenseRecordController {
    private final ExpenseRecordService expenseRecordService;

    public ExpenseRecordController(ExpenseRecordService expenseRecordService) {
        this.expenseRecordService = expenseRecordService;
    }

    @GetMapping("/{id}")
    public ExpenseRecordResponseDTO getById(@AuthenticationPrincipal
    User user, @PathVariable
    String id) {
        return expenseRecordService.getById(user, id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseRecordResponseDTO create(@AuthenticationPrincipal
    User user, @Valid
    @RequestBody
    CreateRequestDTO createRequestDTO) {
        return expenseRecordService.create(user, createRequestDTO);
    }

    @PostMapping("/getAfter")
    public List<ExpenseRecordResponseDTO> getAfter(
            @AuthenticationPrincipal
            User user,
            @Valid
            @RequestBody
            GetAfterRequestDTO getAfterRequestDTO) {
        return expenseRecordService.getAfter(user, getAfterRequestDTO.date());
    }

    @GetMapping("/getAll")
    public List<ExpenseRecordResponseDTO> getAll(@AuthenticationPrincipal
    User user) {
        return expenseRecordService.getAll(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@AuthenticationPrincipal
    User user, @PathVariable
    String id) {
        expenseRecordService.deleteById(user, id);

        return ResponseEntity.noContent().build();
    }
}
