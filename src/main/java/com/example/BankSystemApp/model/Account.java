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
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.00", message = "Balance must be greater than or equal to 0.00")
    private BigDecimal balance;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public Account(String name, BigDecimal balance, List<Transaction> transactions) {
        this.name = name;
        this.balance = balance;
        this.transactions = transactions;
    }
}
