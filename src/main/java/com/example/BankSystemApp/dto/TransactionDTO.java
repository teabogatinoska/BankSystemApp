package com.example.BankSystemApp.dto;

import com.example.BankSystemApp.model.FeeType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionDTO {
    private Long bankId;
    private BigDecimal amount;
    private Long originatingAccountId;
    private Long resultingAccountId;
    private String transactionReason;
    private FeeType feeType;
}
