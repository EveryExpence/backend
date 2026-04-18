package com.every.expence.account;

import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.every.expence.account.dto.AccountResponseDTO;
import com.every.expence.account.dto.CreateAccountRequestDTO;
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

        if (name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }

        Account account = new Account(
            user.getId(),
            name,
            currency,
            createAccountRequestDTO.balance()
        );

        Account saved = accountRepository.save(account);

        return new AccountResponseDTO(
            saved.getId(),
            saved.getName(),
            saved.getCurrency(),
            saved.getBalance(),
            saved.getCreatedAt()
        );
    }

    public List<AccountResponseDTO> getAll(User user) {
        return accountRepository.findAllByOwnerId(user.getId())
            .stream()
            .map(account -> new AccountResponseDTO(
                account.getId(),
                account.getName(),
                account.getCurrency(),
                account.getBalance(),
                account.getCreatedAt()
            ))
            .toList();
    }

    public AccountResponseDTO getById(User user, String accountId) {
        Account account = accountRepository.findByIdAndOwnerId(accountId, user.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        return new AccountResponseDTO(
            account.getId(),
            account.getName(),
            account.getCurrency(),
            account.getBalance(),
            account.getCreatedAt()
        );
    }

    public void deleteById(User user, String accountId) {
        Account account = accountRepository.findByIdAndOwnerId(accountId, user.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        accountRepository.delete(account);
    }
}