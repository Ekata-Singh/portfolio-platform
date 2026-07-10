package com.ektasingh.portfolio.config;

import com.ektasingh.portfolio.auth.entity.Role;
import com.ektasingh.portfolio.auth.entity.User;
import com.ektasingh.portfolio.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AdminDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.count() == 0) {

            User admin = new User();

            admin.setUsername("ekta");

            admin.setEmail("ekta@example.com");

            admin.setPassword(
                    passwordEncoder.encode("Admin@123"));

            admin.setRole(Role.ADMIN);

            admin.setEnabled(true);

            admin.setCreatedAt(LocalDateTime.now());

            admin.setUpdatedAt(LocalDateTime.now());

            userRepository.save(admin);

            System.out.println("Default admin created.");
        }
    }
}