package com.example.BankSystemApp.service.implementation;

import com.example.BankSystemApp.dto.AccountDTO;
import com.example.BankSystemApp.dto.TransactionDTO;
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
    public void createAccount(AccountDTO accountDTO) {
        try {
            Bank bank = bankRepository.findById(accountDTO.getBankId())
                    .orElseThrow(() -> new BankNotFoundException("Bank not found with id: " + accountDTO.getBankId()));
            Account account = new Account( accountDTO.getName(), accountDTO.getBalance(), bank);

            this.accountRepository.save(account);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create account: " + e.getMessage(), e);
        }
    }


    @Override
    public List<TransactionDTO> getTransactionsForAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));
        Long bankId = account.getBank().getBankId();
        List<Transaction> outgoingTransactions = account.getOutgoingTransactions();
        List<Transaction> incomingTransactions = account.getIncomingTransactions();

        List<Transaction> mergedTransactions = new ArrayList<>();
        mergedTransactions.addAll(outgoingTransactions);
        mergedTransactions.addAll(incomingTransactions);

        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        for (Transaction transaction : mergedTransactions) {
            TransactionDTO transactionDTO = mapTransactionToDTO(transaction, bankId );
            transactionDTOList.add(transactionDTO);
        }
        return transactionDTOList;
    }

    @Override
    public BigDecimal getAccountBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));
        return account.getBalance();
    }

    private TransactionDTO mapTransactionToDTO(Transaction transaction, Long bankId) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setBankId(bankId);
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setOriginatingAccountId(transaction.getOriginatingAccount().getAccountId());
        transactionDTO.setResultingAccountId(transaction.getResultingAccount().getAccountId());
        transactionDTO.setTransactionReason(transaction.getTransactionReason());
        transactionDTO.setFeeType(transaction.getFeeType());
        return transactionDTO;
    }
}
