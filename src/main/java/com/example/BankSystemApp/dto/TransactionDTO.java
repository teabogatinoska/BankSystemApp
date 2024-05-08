package com.example.BankSystemApp.dto;

import com.example.BankSystemApp.model.FeeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long bankId;
    private BigDecimal amount;
    private Long originatingAccountId;
    private Long resultingAccountId;
    private String transactionReason;
    private FeeType feeType;
}
