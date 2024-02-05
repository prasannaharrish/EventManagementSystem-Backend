package com.project.eventManagement.password;

import java.io.UnsupportedEncodingException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.exception.InvalidTokenException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class PasswordResetTokenService {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public void assignTokenToUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);

    }

    public boolean isValid(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            throw new InvalidTokenException("The provided Token is Invalid");
        }

        if (passwordResetToken.getExpirationTime().toInstant().isBefore(Instant.now())) {

            throw new InvalidTokenException("The token has Expired.Please resend the link to reset your password");

        }

        return true;

    }

    public User findUserByToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken == null) {
            throw new InvalidTokenException("The provided Token is Invalid");
        }
        User user = passwordResetToken.getUser();

        return user;
    }

    public void sendEmail(String resetLink, User user) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Verification";
        String senderName = "EventHub - An event repository!";
        String mailContent = "<p><b> Hi" + user.getFirstName() + ",</b></p>" +
                "<p><b>You have requested to reset your password.</b>" + ""
                + " Please follow the link to reset your password.</p>" + "<a href = \"" + resetLink
                + "\" >Reset Password</a>" + "<p>From, EventHub - An event Management Service.";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("eventhub72@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);

    }
}
