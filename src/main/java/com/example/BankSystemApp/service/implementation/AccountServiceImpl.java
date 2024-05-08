package com.example.BankSystemApp.service.implementation;

import com.example.BankSystemApp.dto.AccountDTO;
import com.example.BankSystemApp.exception.AccountNotFoundException;
import com.example.BankSystemApp.exception.BankNotFoundException;
import com.example.BankSystemApp.model.Account;
import com.example.BankSystemApp.model.Bank;
import com.example.BankSystemApp.model.Transaction;
import com.example.BankSystemApp.repository.AccountRepository;
import com.example.BankSystemApp.repository.BankRepository;
import com.example.BankSystemApp.repository.TransactionRepository;
import com.example.BankSystemApp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;


    private BankRepository bankRepository;

    public AccountServiceImpl(AccountRepository accountRepository, BankRepository bankRepository) {
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public Account createAccount(AccountDTO accountDTO) {
        try {
            Bank bank = bankRepository.findById(accountDTO.getBankId())
                    .orElseThrow(() -> new BankNotFoundException("Bank not found with id: " + accountDTO.getBankId()));
            Account account = new Account(accountDTO.getName(), accountDTO.getBalance(), bank);
            return accountRepository.save(account);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create account: " + e.getMessage(), e);
        }
    }


    @Override
    public List<Transaction> getTransactionsForAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));

        List<Transaction> outgoingTransactions = account.getOutgoingTransactions();
        List<Transaction> incomingTransactions = account.getIncomingTransactions();

        List<Transaction> mergedTransactions = new ArrayList<>();
        mergedTransactions.addAll(outgoingTransactions);
        mergedTransactions.addAll(incomingTransactions);

        return mergedTransactions;
    }

    @Override
    public BigDecimal getAccountBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));
        return account.getBalance();
    }
}
