package com.example.BankSystemApp.web;

import com.example.BankSystemApp.dto.TransactionDTO;
import com.example.BankSystemApp.exception.AccountNotFoundException;
import com.example.BankSystemApp.exception.InsufficientFundsException;
import com.example.BankSystemApp.model.Account;
import com.example.BankSystemApp.model.Bank;
import com.example.BankSystemApp.model.FeeType;
import com.example.BankSystemApp.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/banks")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        try {
            Bank createdBank = bankService.createBank(bank);
            return new ResponseEntity<>(createdBank, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{bankId}/accounts")
    public ResponseEntity<List<Account>> getAccountsForBank(@PathVariable Long bankId) {
        try {
            List<Account> accounts = bankService.getAllAccounts(bankId);
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/perform")
    public ResponseEntity<String> performTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {
            bankService.performTransaction(transactionDTO);
            return ResponseEntity.ok("Transaction performed successfully");
        } catch (AccountNotFoundException | InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{bankId}/totalFeeAmount")
    public ResponseEntity<BigDecimal> getTotalTransactionFeeAmount(@PathVariable Long bankId) {
        try {
            BigDecimal totalFeeAmount = bankService.getTotalTransactionFeeAmount(bankId);
            return new ResponseEntity<>(totalFeeAmount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{bankId}/totalTransferAmount")
    public ResponseEntity<BigDecimal> getTotalTransferAmount(@PathVariable Long bankId) {
        try {
            BigDecimal totalTransferAmount = bankService.getTotalTransferAmount(bankId);
            return new ResponseEntity<>(totalTransferAmount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
