package com.younesghu.Ecommerce.config;

import com.younesghu.Ecommerce.user.Role;
import com.younesghu.Ecommerce.user.User;
import com.younesghu.Ecommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email:admin@example.com}")
    private String adminEmail;

    @Value("${admin.password:password123}")
    private String adminPassword;

    @Value("${admin.firstName:Admin}")
    private String adminFirstName;

    @Value("${admin.lastName:User}")
    private String adminLastName;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByRole(Role.ADMIN)) {
            User admin = new User(
                    adminFirstName,
                    adminLastName,
                    adminEmail,
                    passwordEncoder.encode(adminPassword),
                    Role.ADMIN);

            userRepository.save(admin);
            log.info("Admin user created with email: {}", adminEmail);
        } else {
            log.info("Admin user already exists, skipping seeder");
        }
    }
}
