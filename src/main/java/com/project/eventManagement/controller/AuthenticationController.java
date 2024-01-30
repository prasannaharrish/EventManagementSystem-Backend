package com.project.eventManagement.controller;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.eventManagement.dto.LoginDTO;
import com.project.eventManagement.dto.LoginResponseDTO;
import com.project.eventManagement.dto.RegistrationDTO;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.exception.UserNotFoundException;
import com.project.eventManagement.password.PasswordResetRequestDTO;
import com.project.eventManagement.password.PasswordResetTokenService;
import com.project.eventManagement.service.AuthenticationService;
import com.project.eventManagement.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @PostMapping("auth/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid RegistrationDTO registrationDTO) {
        User user = authenticationService.registerUser(registrationDTO.getFirstName(), registrationDTO.getLastName(),
                registrationDTO.getEmail(), registrationDTO.getUsername(), registrationDTO.getPassword(),
                registrationDTO.getPhone());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("auth/login")
    public LoginResponseDTO loginUser(@RequestBody @Valid LoginDTO loginDTO) throws UserNotFoundException {
        return authenticationService.loginUser(loginDTO.getEmail(), loginDTO.getPassword());
    }

    @GetMapping("auth/logout")
    public ResponseEntity<String> logout(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String token = authenticationService.extractTokenFromRequest(authorizationHeader);
        if (token != null) {
            authenticationService.logout(token);
            return new ResponseEntity<>("Loggged out Successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Missing Authorization header", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("admin/register")
    public ResponseEntity<User> registerAdmin(@RequestBody @Valid RegistrationDTO registrationDTO) {
        User user = authenticationService.registerAdmin(registrationDTO.getFirstName(), registrationDTO.getLastName(),
                registrationDTO.getEmail(), registrationDTO.getUsername(), registrationDTO.getPassword(),
                registrationDTO.getPhone());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("auth/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody PasswordResetRequestDTO passwordResetRequestDTO,
            final HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        User user = (User) userService.loadUserByUsername(passwordResetRequestDTO.getEmail());
        String resetLink = "";
        if (user != null) {
            String token = UUID.randomUUID().toString();
            passwordResetTokenService.assignTokenToUser(user, token);
            resetLink = sendResetLink(user, getUrl(request), token);
        }

        return new ResponseEntity<>(resetLink + "\nClick on this Link to reset your Password", HttpStatus.ACCEPTED);
    }

    private String sendResetLink(User user, String url, String token)
            throws MessagingException, UnsupportedEncodingException {
        String resetLink = url + "/resetPassword?token=" + token;
        passwordResetTokenService.sendEmail(resetLink, user);
        return resetLink;
    }

    public String getUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
