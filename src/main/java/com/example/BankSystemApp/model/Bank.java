package com.example.BankSystemApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.00")
    private BigDecimal  totalTransactionFeeAmount;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.00")
    private BigDecimal totalTransferAmount;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    private BigDecimal  transactionFlatFeeAmount;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    private BigDecimal  transactionPercentFeeValue;

    public Bank(String name, List<Account> accounts, BigDecimal totalTransactionFeeAmount, BigDecimal totalTransferAmount, BigDecimal transactionFlatFeeAmount, BigDecimal transactionPercentFeeValue) {
        this.name = name;
        this.accounts = accounts;
        this.totalTransactionFeeAmount = totalTransactionFeeAmount;
        this.totalTransferAmount = totalTransferAmount;
        this.transactionFlatFeeAmount = transactionFlatFeeAmount;
        this.transactionPercentFeeValue = transactionPercentFeeValue;
    }
}
