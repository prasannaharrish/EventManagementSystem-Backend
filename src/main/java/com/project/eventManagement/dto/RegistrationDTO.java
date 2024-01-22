package com.project.eventManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class RegistrationDTO {

    @NotEmpty(message = "Firstname cannot be empty ")
    private String firstName;

    @NotEmpty(message = "Lastname cannot be empty ")
    private String lastName;

    @Email(message = "Invalid Email")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid mobile number")
    private String phone;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RegistrationDTO() {
        super();
    }

    public RegistrationDTO(String firstName, String lastName, String email, String username, String password,
            String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "RegistrationDTO [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", username="
                + username + ", password=" + password + "]";
    }

}
