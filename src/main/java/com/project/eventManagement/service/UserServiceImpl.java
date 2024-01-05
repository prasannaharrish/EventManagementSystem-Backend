package com.project.eventManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
