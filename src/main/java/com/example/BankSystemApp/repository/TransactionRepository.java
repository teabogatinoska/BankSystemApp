package com.example.BankSystemApp.repository;

import com.example.BankSystemApp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
