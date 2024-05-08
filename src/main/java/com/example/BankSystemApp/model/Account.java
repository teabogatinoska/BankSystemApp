package com.example.BankSystemApp.model;

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
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private String name;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @Min(value = 0)
    private BigDecimal balance;

    @OneToMany(mappedBy = "originatingAccount", cascade = CascadeType.ALL)
    private List<Transaction> outgoingTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "resultingAccount", cascade = CascadeType.ALL)
    private List<Transaction> incomingTransactions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    public Account(String name, BigDecimal balance, Bank bank) {
        this.name = name;
        this.balance = balance;
        this.bank = bank;
    }

    public Account(String name, BigDecimal balance, List<Transaction> outgoingTransactions, List<Transaction> incomingTransactions, Bank bank) {
        this.name = name;
        this.balance = balance;
        this.outgoingTransactions = outgoingTransactions;
        this.incomingTransactions = incomingTransactions;
        this.bank = bank;
    }

    public Account(BigDecimal balance, String name, Bank bank) {
        this.balance = balance;
        this.name = name;
        this.bank = bank;
    }
}
