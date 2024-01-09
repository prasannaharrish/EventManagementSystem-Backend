package com.project.eventManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.eventManagement.dto.RegistrationDTO;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User registerUser(@RequestBody RegistrationDTO registrationDTO) {
        return authenticationService.registerUser(registrationDTO.getFirstName(), registrationDTO.getLastName(),
                registrationDTO.getEmail(), registrationDTO.getUsername(), registrationDTO.getPassword());
    }
}
