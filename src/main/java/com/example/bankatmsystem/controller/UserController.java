package com.example.bankatmsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.bankatmsystem.dto.UserDTO;
import com.example.bankatmsystem.model.User;
import com.example.bankatmsystem.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("users/form-register")
    public String getRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("content", "fragments/register");
        return "main";
    }

    @PostMapping("/users/register")
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
    User user = new User();
    user.setUsername(userDTO.getUsername());
    user.setPassword(userDTO.getPassword()); // Add password hashing if needed
    user.setApproved(false); // Default value

    userService.saveUser(user); // Ensure this method works correctly

    model.addAttribute("successMessage", "Registration successful. Please log in.");
    model.addAttribute("content", "fragments/login");
    return "main";
}


    @GetMapping("users/login")
    public String getLoginForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("content", "fragments/login");
        return "main";
    }

    @PostMapping("users/login")
    public String loginUser(UserDTO userDTO, Model model) {
        User existingUser = userService.findByUsername(userDTO.getUsername());
        if (existingUser == null || !existingUser.getPassword().equals(userDTO.getPassword())) {
            model.addAttribute("errorMessage", "Invalid username or password");
            model.addAttribute("content", "fragments/login");
            return "main";
        }

        if (!existingUser.getApproved()) {
            model.addAttribute("errorMessage", "Your account has not been approved by the admin.");
            model.addAttribute("content", "fragments/login");
            return "main";
        }

        return "redirect:/home";
    }
}
