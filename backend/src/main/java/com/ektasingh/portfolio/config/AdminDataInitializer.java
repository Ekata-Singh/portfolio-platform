package com.ektasingh.portfolio.config;

import com.ektasingh.portfolio.auth.entity.Role;
import com.ektasingh.portfolio.auth.entity.User;
import com.ektasingh.portfolio.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * Creates the admin account on first start, and optionally resets its password.
 *
 * Credentials come from ADMIN_USERNAME / ADMIN_EMAIL / ADMIN_PASSWORD.
 * Set ADMIN_RESET_PASSWORD=true for one deploy to overwrite the stored
 * password with ADMIN_PASSWORD (useful if you forget it), then unset it.
 *
 * When running locally (the default "local" profile) and no ADMIN_PASSWORD is set,
 * the password defaults to "admin123" so the app works with zero configuration.
 * This default is never used in production.
 */
@Component
@RequiredArgsConstructor
public class AdminDataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminDataInitializer.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final Environment environment;

    private static final String LOCAL_DEFAULT_PASSWORD = "admin123";

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password:}")
    private String adminPassword;

    @Value("${app.admin.reset-password:false}")
    private boolean resetPassword;

    @Override
    public void run(String... args) {

        if (!StringUtils.hasText(adminPassword) && environment.acceptsProfiles(Profiles.of("local"))) {
            adminPassword = LOCAL_DEFAULT_PASSWORD;
            if (userRepository.count() == 0) {
                log.warn("Local mode: creating admin '{}' with default password '{}'. "
                        + "Set ADMIN_PASSWORD to change it.", adminUsername, LOCAL_DEFAULT_PASSWORD);
            }
        }

        if (userRepository.count() == 0) {

            if (!StringUtils.hasText(adminPassword)) {
                log.warn("No users exist and ADMIN_PASSWORD is not set - skipping admin creation. "
                        + "Set ADMIN_PASSWORD and restart to create the admin account.");
                return;
            }

            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true);
            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());

            userRepository.save(admin);

            log.info("Admin account '{}' created.", adminUsername);
            return;
        }

        if (resetPassword && StringUtils.hasText(adminPassword)) {
            userRepository.findByUsername(adminUsername).ifPresentOrElse(user -> {
                user.setPassword(passwordEncoder.encode(adminPassword));
                user.setUpdatedAt(LocalDateTime.now());
                userRepository.save(user);
                log.warn("Password for admin '{}' was reset from ADMIN_PASSWORD. "
                        + "Unset ADMIN_RESET_PASSWORD now.", adminUsername);
            }, () -> log.warn("ADMIN_RESET_PASSWORD is set but user '{}' does not exist.", adminUsername));
        }
    }
}
