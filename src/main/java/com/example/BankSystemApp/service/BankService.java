package com.example.BankSystemApp.service;

import com.example.BankSystemApp.dto.TransactionDTO;
import com.example.BankSystemApp.model.Account;
import com.example.BankSystemApp.model.Bank;
import com.example.BankSystemApp.model.FeeType;

import java.math.BigDecimal;
import java.util.List;

public interface BankService {

    public Bank createBank(Bank bank);

    public List<Account> getAllAccounts(Long bankId);

    public void performTransaction(TransactionDTO transactionDTO);

    public void withdrawMoney(Long accountId, BigDecimal amount);

    public void depositMoney(Long accountId, BigDecimal amount);

    public BigDecimal getTotalTransactionFeeAmount(Long bankId);

    public BigDecimal getTotalTransferAmount(Long bankId);
}
