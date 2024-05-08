package com.example.BankSystemApp.service;

import com.example.BankSystemApp.dto.AccountDTO;
import com.example.BankSystemApp.model.Account;
import com.example.BankSystemApp.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    public Account createAccount(AccountDTO accountDTO);

    public List<Transaction> getTransactionsForAccount(Long accountId);

    public BigDecimal getAccountBalance(Long accountId);
}
