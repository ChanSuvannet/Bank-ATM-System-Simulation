package com.example.bankatmsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.bankatmsystem.dto.UserDTO;
import com.example.bankatmsystem.model.User;
import com.example.bankatmsystem.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("users/form-register")
    public String getProducts(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("content", "fragments/register");
        return "main";
    }

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
}
