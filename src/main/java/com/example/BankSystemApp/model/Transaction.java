package com.example.BankSystemApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
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
    private Long id;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.00")
    private BigDecimal amount;

    private Long originatingAccountId;

    private Long resultingAccountId;

    private String transactionReason;


    public Transaction(BigDecimal amount, Long originatingAccountId, Long resultingAccountId, String transactionReason) {
        this.amount = amount;
        this.originatingAccountId = originatingAccountId;
        this.resultingAccountId = resultingAccountId;
        this.transactionReason = transactionReason;
    }
}
