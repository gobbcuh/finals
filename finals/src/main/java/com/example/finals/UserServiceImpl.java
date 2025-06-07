package com.example.finals;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
    private List<User> users = new ArrayList<>();

    @Override
    public boolean registerUser(User user) {
        LOGGER.info("Attempting to register user with email: " + user.getEmail());
        // Check if email already exists
        for (User existingUser : users) {
            if (existingUser.getEmail().equals(user.getEmail())) {
                LOGGER.warning("Registration failed: Email already registered: " + user.getEmail());
                return false; // Email already registered
            }
        }
        users.add(user);
        LOGGER.info("User registered successfully: " + user.getEmail());
        return true;
    }

    @Override
    public User loginUser(String email, String password) {
        LOGGER.info("Attempting login for email: " + email);
        if (email == null || password == null) {
            LOGGER.warning("Login failed: Email or password is null");
            return null;
        }
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                LOGGER.info("Login successful for email: " + email);
                return user; // Successful login
            }
        }
        LOGGER.warning("Login failed: Invalid credentials for email: " + email);
        return null; // Invalid credentials
    }
}