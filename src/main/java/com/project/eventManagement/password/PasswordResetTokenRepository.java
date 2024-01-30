package com.project.eventManagement.password;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    public PasswordResetToken findByToken(String Token);

}
