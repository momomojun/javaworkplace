package com.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordUtils {

    // USE SHA-256 to hash the password
    // return the hashed password as a Base64 encoded string
    // password: the plain text password
    // return: the hashed password
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无法找到 SHA-256 算法", e);
        }
    }

    // Check if the provided password matches the hashed password
    // password: the plain text password
    // hashedPassword: the stored hashed password
    // return: true if match, false otherwise
    public static boolean checkPassword(String password, String hashedPassword) {
        String hashOfInput = hashPassword(password);
        return hashOfInput.equals(hashedPassword);
    }
}
