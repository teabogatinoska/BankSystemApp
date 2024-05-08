package com.example.BankSystemApp.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BankAccountsDTO {
    private Long accountId;
    private String name;
    private BigDecimal balance;
}
