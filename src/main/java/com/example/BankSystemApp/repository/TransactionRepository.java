package com.example.BankSystemApp.repository;

import com.example.BankSystemApp.model.Account;
import com.example.BankSystemApp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
