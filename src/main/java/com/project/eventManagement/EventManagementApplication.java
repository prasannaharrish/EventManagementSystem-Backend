package com.project.eventManagement;

import java.util.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.eventManagement.entity.Role;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.repository.RoleRepository;
import com.project.eventManagement.repository.UserRepository;

@SpringBootApplication()
public class EventManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		return args -> {
			if (roleRepository.findByAuthority("ADMIN").isPresent())
				return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User("admin", "admin", "admin@gmail.com", "admin", passwordEncoder.encode("admin"),
					roles);
			userRepository.save(admin);

		};
	}

}
