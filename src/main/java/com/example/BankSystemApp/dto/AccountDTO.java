package com.example.BankSystemApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class AccountDTO {

    private Long bankId;
    private String name;
    private BigDecimal balance;
}
