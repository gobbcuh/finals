package com.example.finals;

public interface UserService {
    boolean registerUser(User user);
    User loginUser(String email, String password);
}