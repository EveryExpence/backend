package com.every.expence.account;

import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.every.expence.account.dto.AccountResponseDTO;
import com.every.expence.account.dto.CreateAccountRequestDTO;
import com.every.expence.account.dto.UpdateAccountRequestDTO;
import com.every.expence.user.User;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponseDTO create(User user, CreateAccountRequestDTO createAccountRequestDTO) {
        String name = createAccountRequestDTO.name().trim();
        String currency = createAccountRequestDTO.currency().trim().toUpperCase(Locale.ROOT);

        Account account = new Account(
            createAccountRequestDTO.id(),
            user.getId(),
            name,
            currency,
            createAccountRequestDTO.balance()
        );

        Account saved = accountRepository.save(account);

        return AccountResponseDTO.fromEntity(saved);
    }

    public List<AccountResponseDTO> getAll(User user) {
        return accountRepository.findAllByOwnerId(user.getId())
            .stream()
            .map(AccountResponseDTO::fromEntity)
            .toList();
    }

    public AccountResponseDTO getById(User user, String accountId) {
        Account account = accountRepository.findByIdAndOwnerId(accountId, user.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        return AccountResponseDTO.fromEntity(account);
    }

    public void deleteById(User user, String accountId) {
        Account account = accountRepository.findByIdAndOwnerId(accountId, user.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        accountRepository.delete(account);
    }

    public AccountResponseDTO update(User user, String accountId, UpdateAccountRequestDTO updateAccountRequestDTO) {
        Account account = accountRepository.findByIdAndOwnerId(accountId, user.getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        String name = updateAccountRequestDTO.name().trim();
        String currency = updateAccountRequestDTO.currency().trim().toUpperCase(Locale.ROOT);

        account.setName(name);
        account.setCurrency(currency);
        account.setBalance(updateAccountRequestDTO.balance());

        Account saved = accountRepository.save(account);
        return AccountResponseDTO.fromEntity(saved);
    }
}