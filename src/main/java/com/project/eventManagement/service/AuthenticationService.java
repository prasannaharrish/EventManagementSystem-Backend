package com.project.eventManagement.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.eventManagement.dto.LoginResponseDTO;
import com.project.eventManagement.entity.Event;
import com.project.eventManagement.entity.Role;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.exception.UserNotFoundException;
import com.project.eventManagement.repository.RoleRepository;
import com.project.eventManagement.repository.UserRepository;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public User registerUser(String firstName, String lastName, String email, String username, String password,
            String phone) {

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Event> participatingEvents = new HashSet<>();

        return userRepository.saveAndFlush(
                new User(firstName, lastName, email, username, encodedPassword, phone, userRole, participatingEvents));

    }

    public User registerAdmin(String firstName, String lastName, String email, String username, String password,
            String phone) {

        String encodedPassword = passwordEncoder.encode(password);
        Role adminRole = roleRepository.findByAuthority("ADMIN").get();

        Set<Event> participatingEvents = new HashSet<>();

        return userRepository.saveAndFlush(
                new User(firstName, lastName, email, username, encodedPassword, phone, adminRole, participatingEvents));

    }

    public LoginResponseDTO loginUser(String email, String password) throws UserNotFoundException {

        try {
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            String token = tokenService.generateJwt(auth);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            return new LoginResponseDTO(user, token);
        } catch (AuthenticationException exception) {
            throw new UserNotFoundException("Invalid credentials");

        }

    }
}
