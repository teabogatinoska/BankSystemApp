package com.example.BankSystemApp.service;

import com.example.BankSystemApp.dto.BankAccountsDTO;
import com.example.BankSystemApp.dto.BankDTO;
import com.example.BankSystemApp.dto.TransactionDTO;
import com.example.BankSystemApp.model.Account;
import com.example.BankSystemApp.model.Bank;
import com.example.BankSystemApp.model.FeeType;

import java.math.BigDecimal;
import java.util.List;

public interface BankService {

     BankDTO createBank(Bank bank);

     List<BankAccountsDTO> getAllAccounts(Long bankId);

     void performTransaction(TransactionDTO transactionDTO);

     void withdrawMoney(Long accountId, BigDecimal amount);

     void depositMoney(Long accountId, BigDecimal amount);

     BigDecimal getTotalTransactionFeeAmount(Long bankId);

     BigDecimal getTotalTransferAmount(Long bankId);
}
