package com.example.BankSystemApp.service;

import com.example.BankSystemApp.dto.AccountDTO;
import com.example.BankSystemApp.dto.TransactionDTO;


import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

     void createAccount(AccountDTO accountDTO);

     List<TransactionDTO> getTransactionsForAccount(Long accountId);

     BigDecimal getAccountBalance(Long accountId);
}
