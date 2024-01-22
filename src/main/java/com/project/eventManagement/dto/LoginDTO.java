package com.project.eventManagement.dto;

import jakarta.validation.constraints.Email;

public class LoginDTO {

    @Email(message = "Enter a valid Email")
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO [username=" + email + ", password=" + password + "]";
    }

}
