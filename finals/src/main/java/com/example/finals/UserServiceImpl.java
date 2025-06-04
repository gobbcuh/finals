package com.example.finals;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private List<User> users = new ArrayList<>();

    @Override
    public boolean registerUser(User user) {
        // Check if email already exists
        for (User existingUser : users) {
            if (existingUser.getEmail().equals(user.getEmail())) {
                return false; // Email already registered
            }
        }
        users.add(user);
        return true;
    }

    @Override
    public User loginUser(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user; // Successful login
            }
        }
        return null; // Invalid credentials
    }
}