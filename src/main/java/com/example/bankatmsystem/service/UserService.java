package com.example.bankatmsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankatmsystem.dto.UserDTO;
import com.example.bankatmsystem.model.User;
import com.example.bankatmsystem.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    // Create a new user
    public void createUser(UserDTO userDTO) {
        // Check if username already exists
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists. Please choose a different one.");
        }

        // Save the user if the username is unique
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setApproved(false); // Admin approval required by default
        userRepository.save(user);
    }

    // Find user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Approve user by admin
    public User approveUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setApproved(true);
        return userRepository.save(user);
    }

    // Get a single user by ID
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Get all users
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User authenticateUser(String username, String password) {
        // Find the user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the password matches
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        // Return the user if authentication is successful
        return user;
    }
}
