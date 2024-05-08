package com.example.BankSystemApp.dto;

import com.example.BankSystemApp.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BankDTO {
    private Long bankId;
    private String name;
}
