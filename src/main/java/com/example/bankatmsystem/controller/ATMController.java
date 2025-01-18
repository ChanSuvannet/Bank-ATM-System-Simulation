package com.example.bankatmsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankatmsystem.service.ATMService;

@RestController
@RequestMapping("/atm")
public class ATMController {

    @Autowired
    private ATMService atmService;

    @PostMapping("/transaction")
    public String performTransaction(@RequestParam Long userId, @RequestParam String type, @RequestParam Double amount) {
        return atmService.performTransaction(userId, type, amount);
    }
}
