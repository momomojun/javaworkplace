package com.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// to manage user registration and login,
// persisting user data to users.json file.
public class UserService {

    private static final String USER_FILE = "users.json";
    private Map<String, User> users; // username -> User
    private Gson gson = new Gson();

    public UserService() {
        this.users = loadUsers();
    }

    // Load user data from users.json
    private Map<String, User> loadUsers() {
        try {
            if (!Files.exists(Paths.get(USER_FILE))) {
                return new ConcurrentHashMap<>(); // if file doesn't exist, return empty map
            }
            try (FileReader reader = new FileReader(USER_FILE)) {
                Type type = new TypeToken<Map<String, User>>() {}.getType();
                Map<String, User> loadedUsers = gson.fromJson(reader, type);
                if (loadedUsers == null) {
                    return new ConcurrentHashMap<>();
                }
                // Ensure it's thread-safe
                return new ConcurrentHashMap<>(loadedUsers);
            }
        } catch (IOException e) {
            System.err.println("loading user info failed: " + e.getMessage());
            return new ConcurrentHashMap<>();
        }
    }

    // Save the current user map back to users.json
    private synchronized void saveUsers() {
        try (FileWriter writer = new FileWriter(USER_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("storing user info failed: " + e.getMessage());
        }
    }

    // register a new user
    // return: true if registration successful
    public synchronized boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false; // username already exists
        }
        if (username.isEmpty() || password.isEmpty()) {
            return false; // username or password cannot be empty
        }

        String hashedPassword = PasswordUtils.hashPassword(password);
        User newUser = new User(username, hashedPassword);
        users.put(username, newUser);
        saveUsers(); // save to file
        return true;
    }

    // login an existing user
    // return: true if login successful
    public synchronized boolean login(String username, String password) {
        User user = users.get(username);
        if (user == null) {
            return false; // user not found
        }
        
        return PasswordUtils.checkPassword(password, user.getHashedPassword());
    }
}
