package com.project.eventManagement.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.eventManagement.dto.LoginDTO;
import com.project.eventManagement.dto.RegistrationDTO;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(RegistrationDTO registrationDTO) {
        if ((userRepository.existsByUsername(registrationDTO.getUsername()))
                || userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new RuntimeException("User already Exists");
        }

        User user = new User(registrationDTO.getUsername(), registrationDTO.getPassword(), registrationDTO.getEmail());
        return userRepository.save(user);
    };

    public User loginUser(LoginDTO loginDTO) {
        User user = userRepository.loginUser(loginDTO.getUsername(), loginDTO.getPassword())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user;

    }
}
