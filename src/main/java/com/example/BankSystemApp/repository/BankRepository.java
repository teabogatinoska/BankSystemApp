package com.example.BankSystemApp.repository;

import com.example.BankSystemApp.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
