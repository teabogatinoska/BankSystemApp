package com.example.BankSystemApp.repository;

import com.example.BankSystemApp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
