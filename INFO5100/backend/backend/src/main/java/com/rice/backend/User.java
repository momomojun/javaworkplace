package com.rice.backend;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true, nullable = false)
    public String username;

    public String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}