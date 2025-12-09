package com.rice.backend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); // SELECT * FROM users WHERE username = ?
}