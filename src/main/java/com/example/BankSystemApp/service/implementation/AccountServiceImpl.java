package com.example.BankSystemApp.service.implementation;

import com.example.BankSystemApp.exception.AccountNotFoundException;
import com.example.BankSystemApp.model.Account;
import com.example.BankSystemApp.model.Transaction;
import com.example.BankSystemApp.repository.AccountRepository;
import com.example.BankSystemApp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(Account account) {
        try {
            return accountRepository.save(account);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create account: " + e.getMessage());
        }
    }

    @Override
    public List<Transaction> getTransactionsForAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));
        return account.getTransactions();
    }

    @Override
    public BigDecimal getAccountBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));
        return account.getBalance();
    }
}
