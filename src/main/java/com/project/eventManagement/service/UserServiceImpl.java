package com.project.eventManagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.eventManagement.controller.LoginResponse;
import com.project.eventManagement.dto.LoginDTO;
import com.project.eventManagement.dto.RegistrationDTO;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder PasswordEncoder;

    @Override
    public User registerUser(RegistrationDTO registrationDTO) {
        User user = new User(registrationDTO.getFirstName(), registrationDTO.getLastName(), registrationDTO.getEmail(),
                registrationDTO.getUsername(), PasswordEncoder.encode(registrationDTO.getPassword()));

        return userRepository.save(user);

    }

    @Override
    public LoginResponse loginUser(LoginDTO loginDTO) {
        String message = "";
        User checkUser = userRepository.existsByUsername(loginDTO.getUsername());
        if (checkUser != null) {
            String checkPassword = loginDTO.getPassword();
            String actualPassword = checkUser.getPassword();
            Boolean isPasswordRight = PasswordEncoder.matches(actualPassword, checkPassword);
            if (isPasswordRight) {
                Optional<User> user = userRepository.loginUser(loginDTO.getUsername(), loginDTO.getPassword());
                if (user.isPresent()) {
                    return new LoginResponse("Login success", true);

                } else {
                    return new LoginResponse("Login failed", false);
                }
            } else {
                return new LoginResponse("password not match", false);
            }
        } else {

            return new LoginResponse("User not exists", false);
        }
    }

}
