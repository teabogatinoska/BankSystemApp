package com.example.BankSystemApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
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
    private Long bankId;

    private String name;

    @NotNull
    @Min(value = 0)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal  totalTransactionFeeAmount;

    @NotNull
    @Min(value = 0)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal totalTransferAmount;

    @NotNull
    @Min(value = 0)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal  transactionFlatFeeAmount;

    @NotNull
    @Min(value = 0)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal  transactionPercentFeeValue;

    @JsonIgnore
    @OneToMany(mappedBy = "bank")
    private List<Account> accounts = new ArrayList<>();



    public Bank(String name, List<Account> accounts, BigDecimal totalTransactionFeeAmount, BigDecimal totalTransferAmount, BigDecimal transactionFlatFeeAmount, BigDecimal transactionPercentFeeValue) {
        this.name = name;
        this.accounts = accounts;
        this.totalTransactionFeeAmount = totalTransactionFeeAmount;
        this.totalTransferAmount = totalTransferAmount;
        this.transactionFlatFeeAmount = transactionFlatFeeAmount;
        this.transactionPercentFeeValue = transactionPercentFeeValue;
    }
}
