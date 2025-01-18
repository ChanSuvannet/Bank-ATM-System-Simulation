package com.example.bankatmsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankatmsystem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
