package com.example.bankatmsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankatmsystem.model.Transaction;
import com.example.bankatmsystem.model.User;
import com.example.bankatmsystem.repository.TransactionRepository;
import com.example.bankatmsystem.repository.UserRepository;
@Service
public class ATMService {

    @Autowired
    private SemaphoreService semaphoreService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public String performTransaction(Long userId, String type, Double amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getApproved()) {
            return "User not approved by admin.";
        }

        boolean accessGranted = semaphoreService.acquire();
        if (!accessGranted) {
            return "System is currently in use. Please wait.";
        }

        try {
            // Simulate the transaction
            Transaction transaction = new Transaction();
            transaction.setType(type);
            transaction.setAmount(amount);
            transaction.setUser(user);

            transactionRepository.save(transaction);

            return "Transaction successful: " + type + " of " + amount;
        } finally {
            semaphoreService.release(); // Ensure the Semaphore is released
        }
    }
    public String accessATM(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getApproved()) {
            return "User not approved by admin.";
        }
        // ATM access logic here (e.g., perform transactions)
        return "ATM access granted.";
    }
}
