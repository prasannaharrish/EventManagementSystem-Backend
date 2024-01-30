package com.project.eventManagement.password;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.project.eventManagement.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Integer tokenId;

    @Column(name = "token")
    private String token;

    @Column(name = "expiration_time")
    private Timestamp expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expirationTime = this.setTokenExpirationTime();
    }

    public PasswordResetToken(String token) {
        this.token = token;
        this.expirationTime = this.setTokenExpirationTime();
    }

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Timestamp expirationTime) {
        this.expirationTime = expirationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private Timestamp setTokenExpirationTime() {
        Instant currentInstant = Instant.now();
        Instant expirationInstant = currentInstant.plusSeconds(300);
        Timestamp expirationTime = Timestamp.from(expirationInstant);
        return expirationTime;
    }
}
