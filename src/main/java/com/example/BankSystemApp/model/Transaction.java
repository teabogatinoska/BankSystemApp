package com.example.BankSystemApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @Min(value = 0)
    private BigDecimal amount;

    private String transactionReason;

    @ManyToOne
    @JoinColumn(name = "originating_account_id")
    private Account originatingAccount;

    @ManyToOne
    @JoinColumn(name = "resulting_account_id")
    private Account resultingAccount;

    private FeeType feeType;


    public Transaction(BigDecimal amount, String transactionReason, Account originatingAccount, Account resultingAccount, FeeType feeType) {
        this.amount = amount;
        this.transactionReason = transactionReason;
        this.originatingAccount = originatingAccount;
        this.resultingAccount = resultingAccount;
        this.feeType = feeType;
    }

    public Transaction(BigDecimal amount, String withdrawal, Account account, Account account1) {
        this.amount = amount;
        this.transactionReason = transactionReason;
        this.originatingAccount = originatingAccount;
        this.resultingAccount = resultingAccount;
    }
}
