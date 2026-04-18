package com.every.expence.account;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.every.expence.account.dto.AccountResponseDTO;
import com.every.expence.account.dto.CreateAccountRequestDTO;
import com.every.expence.user.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/accounts/create")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDTO create(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateAccountRequestDTO createAccountRequestDTO) {
        return accountService.create(user, createAccountRequestDTO);
    }

    @DeleteMapping("/account/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal User user,
            @PathVariable String id) {
        accountService.deleteById(user, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/accounts/getAll")
    public List<AccountResponseDTO> getAll(@AuthenticationPrincipal User user) {
        return accountService.getAll(user);
    }

    @GetMapping("/accounts/get/{id}")
    public AccountResponseDTO getById(
            @AuthenticationPrincipal User user,
            @PathVariable String id) {
        return accountService.getById(user, id);
    }
}