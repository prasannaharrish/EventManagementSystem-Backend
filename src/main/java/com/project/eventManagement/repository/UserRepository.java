package com.project.eventManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.eventManagement.entity.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User existsByUsername(String username);

    User existsByEmail(String email);



    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
    Optional<User> loginUser(@Param("username") String username, @Param("password") String password);

}
