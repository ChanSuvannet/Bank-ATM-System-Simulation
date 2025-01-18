package com.example.bankatmsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankatmsystem.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
