package com.example.bankatmsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.bankatmsystem.dto.UserDTO;
import com.example.bankatmsystem.model.User;
import com.example.bankatmsystem.service.SemaphoreService;
import com.example.bankatmsystem.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SemaphoreService semaphoreService;

    // Display registration form
    @GetMapping("/register")
    public String getRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("content", "fragments/register");
        return "main"; // Returns the main layout with the registration fragment
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("content", "fragments/login");
        return "main"; // Returns the main layout with the registration fragment
    }

    @PostMapping("/register")
    public String createUser(
            @Valid @ModelAttribute("userDTO") UserDTO userDTO,
            BindingResult bindingResult,
            Model model) {
        System.err.println(userDTO); // Debugging purposes
        if (bindingResult.hasErrors()) {
            model.addAttribute("content", "fragments/register");
            return "main"; // Return to form with validation errors
        }

        try {
            userService.createUser(userDTO); 
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage()); // Show specific error (e.g., username exists)
            model.addAttribute("content", "fragments/register");
            return "main"; // Return to form with an error message
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            model.addAttribute("content", "fragments/register");
            return "main"; // Generic error handling
        }
    }

    @PostMapping("/login")
    public RedirectView loginUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Check if semaphore allows access
        if (!semaphoreService.acquire()) {
            redirectAttributes.addFlashAttribute("error", "System is busy, please try again later.");
            return new RedirectView("/login"); // Redirect to login if system is busy
        }

        try {
            // Authenticate user
            User user = userService.authenticateUser(userDTO.getUsername(), userDTO.getPassword());

            if (user != null) {
                // Successful login
                redirectAttributes.addFlashAttribute("message", "Login successful! Redirecting to ATM...");
                redirectAttributes.addFlashAttribute("username", user.getUsername()); // Store the username in session
                return new RedirectView("/atm"); // Redirect to ATM page
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid username or password");
                return new RedirectView("/login"); // Redirect back to login page with error
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred: " + e.getMessage());
            return new RedirectView("/login"); // Redirect back to login page with error message
        } finally {
            semaphoreService.release(); // Ensure semaphore is released after the login attempt
        }
    }

}
