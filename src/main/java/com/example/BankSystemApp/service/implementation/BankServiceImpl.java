package com.example.BankSystemApp.service.implementation;

import com.example.BankSystemApp.dto.TransactionDTO;
import com.example.BankSystemApp.exception.AccountNotFoundException;
import com.example.BankSystemApp.exception.BankNotFoundException;
import com.example.BankSystemApp.exception.InsufficientFundsException;
import com.example.BankSystemApp.model.Account;
import com.example.BankSystemApp.model.Bank;
import com.example.BankSystemApp.model.FeeType;
import com.example.BankSystemApp.model.Transaction;
import com.example.BankSystemApp.repository.AccountRepository;
import com.example.BankSystemApp.repository.BankRepository;
import com.example.BankSystemApp.repository.TransactionRepository;
import com.example.BankSystemApp.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public BankServiceImpl(BankRepository bankRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.bankRepository = bankRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Bank createBank(Bank bank) {
        try {
            return bankRepository.save(bank);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create bank: " + e.getMessage());
        }
    }

    @Override
    public List<Account> getAllAccounts(Long bankId) {
        Bank bank = this.bankRepository.findById(bankId)
                .orElseThrow(() -> new BankNotFoundException("Bank not found with id: " + bankId));
        return bank.getAccounts();
    }

    @Override
    public void performTransaction(TransactionDTO transactionDTO) {
        Account originatingAccount = accountRepository.findById(transactionDTO.getOriginatingAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Originating account not found with ID: " + transactionDTO.getOriginatingAccountId()));

        Account resultingAccount = accountRepository.findById(transactionDTO.getResultingAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Resulting account not found with ID: " + transactionDTO.getResultingAccountId()));

        Bank bank = bankRepository.findById(transactionDTO.getBankId()).orElseThrow(() -> new BankNotFoundException("Bank not found with id: " + transactionDTO.getBankId()));
        BigDecimal fee = calculateFee(transactionDTO.getAmount(), transactionDTO.getFeeType(), bank);
        bank.setTotalTransactionFeeAmount(bank.getTotalTransactionFeeAmount().add(fee));
        bank.setTotalTransferAmount(bank.getTotalTransferAmount().add(transactionDTO.getAmount()));
        bankRepository.save(bank);


        BigDecimal totalAmountWithFee = transactionDTO.getAmount().add(fee);
        if (originatingAccount.getBalance().compareTo(totalAmountWithFee) < 0) {
            throw new InsufficientFundsException("Insufficient funds in the originating account");
        }

        Transaction transaction = new Transaction(transactionDTO.getAmount(), transactionDTO.getOriginatingAccountId(), transactionDTO.getResultingAccountId(), transactionDTO.getTransactionReason());
        transactionRepository.save(transaction);

        originatingAccount.setBalance(originatingAccount.getBalance().subtract(totalAmountWithFee));
        accountRepository.save(originatingAccount);

        resultingAccount.setBalance(resultingAccount.getBalance().add(transactionDTO.getAmount()));
        accountRepository.save(resultingAccount);
    }

    @Override
    public void withdrawMoney(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in the account");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction(amount.negate(), accountId, null, "Withdrawal");
        transactionRepository.save(transaction);
    }

    @Override
    public void depositMoney(Long accountId, BigDecimal amount) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction(amount, null, accountId, "Deposit");
        transactionRepository.save(transaction);
    }

    @Override
    public BigDecimal getTotalTransactionFeeAmount(Long bankId) {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new BankNotFoundException("Bank not found with id: " + bankId));
        return bank.getTotalTransactionFeeAmount();
    }

    @Override
    public BigDecimal getTotalTransferAmount(Long bankId) {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new BankNotFoundException("Bank not found with id: " + bankId));
        return bank.getTotalTransferAmount();
    }

    private BigDecimal calculateFee(BigDecimal amount, FeeType feeType, Bank bank) {
        if (feeType == FeeType.FLAT) {
            return bank.getTransactionFlatFeeAmount();
        } else if (feeType == FeeType.PERCENT) {
            return amount.multiply(bank.getTransactionPercentFeeValue().divide(BigDecimal.valueOf(100)));
        } else {
            throw new IllegalArgumentException("Invalid fee type");
        }
    }
}
