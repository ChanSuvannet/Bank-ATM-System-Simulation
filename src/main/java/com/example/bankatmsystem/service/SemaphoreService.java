package com.example.bankatmsystem.service;

import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SemaphoreService {

    private static final Logger logger = LoggerFactory.getLogger(SemaphoreService.class);
    private final Semaphore semaphore = new Semaphore(1); // Limit to 1 user at a time
    private boolean isUserLoggedIn = false; // Track if a user is logged in

    public boolean acquire() {
        try {
            if (isUserLoggedIn) {
                logger.warn("System is busy. Another user is already logged in.");
                return false; // Deny access if a user is already logged in
            }

            if (semaphore.tryAcquire()) {
                logger.info("Semaphore acquired. Access granted.");
                isUserLoggedIn = true; // Set the flag to true when a user is logged in
                return true; // Access granted
            } else {
                logger.warn("Semaphore busy. Access denied.");
                return false; // Access denied
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            logger.error("Semaphore acquisition interrupted.", e);
            return false;
        }
    }

    public void release() {
        isUserLoggedIn = false; // Reset the login flag when a user logs out or finishes
        semaphore.release();
        logger.info("Semaphore released.");
    }
}
