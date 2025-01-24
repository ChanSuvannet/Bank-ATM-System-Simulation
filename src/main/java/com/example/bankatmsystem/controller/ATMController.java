package com.example.bankatmsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.bankatmsystem.service.SemaphoreService;

@Controller
public class ATMController {

    @Autowired
    private SemaphoreService semaphoreService;

    // Show ATM page with username and options (GET request)
    @GetMapping("/atm")
    public String getATMPage(Model model) {

        // Check if a user is already logged in
        if (!semaphoreService.acquire()) {
            model.addAttribute("error", "System is busy, another user is already accessing the ATM.");
            return "login";
        }

        // Add the logged-in username to the model
        model.addAttribute("username", "User");
        model.addAttribute("content", "fragments/atm");

        return "main";
    }

    // Check Balance (GET request)
    @GetMapping("/atm/check-balance")
    public String checkBalance(Model model) {
        // Logic to check balance here
        model.addAttribute("balance", 1000); // Example balance, replace with actual logic
        return "atmBalance"; // A page showing the balance
    }

    // Withdraw Funds (GET request)
    @GetMapping("/atm/withdraw")
    public String withdrawFunds(Model model) {
        // Logic for withdrawal here
        return "atmWithdraw"; // A page for withdrawal process
    }

    // Deposit Funds (GET request)
    @GetMapping("/atm/deposit")
    public String depositFunds(Model model) {
        // Logic for deposit here
        return "atmDeposit"; // A page for deposit process
    }

    // Logout (GET request)
    @GetMapping("/atm/logout")
    public String logout(Model model) {
        // Logic to handle logout here
        return "redirect:/login"; // Redirect back to login page
    }
}
