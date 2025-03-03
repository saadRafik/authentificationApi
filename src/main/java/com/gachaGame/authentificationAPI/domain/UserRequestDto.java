package com.gachaGame.authentificationAPI.domain;

public class UserRequestDto {
    private String username;
    private String password;

    // Getters et Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
