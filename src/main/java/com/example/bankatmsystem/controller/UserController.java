package com.example.bankatmsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankatmsystem.model.User;
import com.example.bankatmsystem.service.ATMService;
import com.example.bankatmsystem.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ATMService atmService;

    // Register a new user
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // User login (For simplicity, check only username)
    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
            return "Invalid credentials.";
        }
        return "Login successful!";
    }

    // User requests ATM access
    @GetMapping("/access/{userId}")
    public String accessATM(@PathVariable Long userId) {
        return atmService.accessATM(userId);
    }
}
