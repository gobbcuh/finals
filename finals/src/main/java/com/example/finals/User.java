package com.example.finals;

public class User {
    private String fullName;
    private String email;
    private String password;
    private String phone;

    // Constructor
    public User(String fullName, String email, String password, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.password = password; // In a real app, hash the password
        this.phone = phone;
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}