package com.project.eventManagement.controller;

import com.project.eventManagement.dto.LoginDTO;
import com.project.eventManagement.dto.RegistrationDTO;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegistrationDTO registrationDTO) {
        return new ResponseEntity<>(userService.registerUser(registrationDTO), HttpStatus.OK);

    }

    // @PostMapping("/login")
    // public ResponseEntity<User> login(@RequestBody LoginDTO loginDTO) {
    // User user = userService.loginUser(loginDTO);

    // return new ResponseEntity<>(user, HttpStatus.OK);
    // }

}
