package com.example.FreshFood.config;


import com.example.FreshFood.entity.User;
import com.example.FreshFood.enums.Role;
import com.example.FreshFood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (!userRepository.existsByUsername("admin")) {

            User admin = User.builder()
                    .username("admin")
                    .email("admin@freshfood.com")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("FreshFood Admin")
                    .phone("0123456789")
                    .address("FreshFood")
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build();

            userRepository.save(admin);
        }
    }
}