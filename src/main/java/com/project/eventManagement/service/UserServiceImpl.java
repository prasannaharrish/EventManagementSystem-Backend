package com.project.eventManagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public User registerUser(RegistrationDTO registrationDTO) {
        User user = new User(registrationDTO.getFirstName(), registrationDTO.getLastName(), registrationDTO.getEmail(),
                registrationDTO.getUsername(), registrationDTO.getPassword());

        return userRepository.save(user);

    }

    @Override
    public LoginResponse loginStatus(LoginDTO loginDTO) {
        User checkUser = userRepository.findByUsername(loginDTO.getUsername());
        if (checkUser != null) {
            String checkPassword = loginDTO.getPassword();
            String actualPassword = checkUser.getPassword();
            Boolean isPasswordRight = actualPassword.matches(checkPassword);
            if (isPasswordRight) {
                User loggedInUser = userRepository.findByUsernameAndPassword(loginDTO.getUsername(),
                        loginDTO.getPassword());
                if (loggedInUser != null) {
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
