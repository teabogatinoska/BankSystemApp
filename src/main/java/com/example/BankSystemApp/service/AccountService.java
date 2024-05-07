package com.example.BankSystemApp.service;

import com.example.BankSystemApp.model.Account;
import com.example.BankSystemApp.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    public Account createAccount(Account account);

    public List<Transaction> getTransactionsForAccount(Long accountId);

    public BigDecimal getAccountBalance(Long accountId);
}
