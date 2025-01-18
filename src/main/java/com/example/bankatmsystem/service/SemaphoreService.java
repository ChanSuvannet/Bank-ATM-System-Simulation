package com.example.bankatmsystem.service;

import java.util.concurrent.Semaphore;

import org.springframework.stereotype.Service;

@Service
public class SemaphoreService {

    private final Semaphore semaphore = new Semaphore(1); // Limit to 1 user at a time

    public boolean acquire() {
        try {
            semaphore.acquire();
            return true; // Access granted
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false; // Access denied
        }
    }

    public void release() {
        semaphore.release();
    }
}
