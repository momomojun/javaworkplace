package com.server;

// A simple User class to hold user information
public class User {
    private String username;
    private String hashedPassword;

    // GSON requires a no-argument constructor
    public User() {
    }

    public User(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
}
