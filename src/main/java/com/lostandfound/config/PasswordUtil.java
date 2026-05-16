package com.lostandfound.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * PasswordUtil - Hashes passwords using SHA-256
 * Used in RegisterServlet and LoginServlet
 * Package: com.lostandfound.config
 */
public class PasswordUtil {

    /**
     * Hashes a plain-text password using SHA-256.
     * @param plainPassword the raw password entered by user
     * @return hex string of the hashed password
     */
    public static String hash(String plainPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(plainPassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available.", e);
        }
    }

    /**
     * Verifies a plain password against a stored hash.
     * @param plainPassword  the raw password to check
     * @param hashedPassword the stored hash from the database
     * @return true if they match
     */
    public static boolean verify(String plainPassword, String hashedPassword) {
        return hash(plainPassword).equals(hashedPassword);
    }
}
