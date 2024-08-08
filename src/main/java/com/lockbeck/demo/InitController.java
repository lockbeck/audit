package com.lockbeck.demo;

import com.lockbeck.entities.user.Role;
import com.lockbeck.entities.user.UserEntity;
import com.lockbeck.entities.user.UserRepository;
import com.lockbeck.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/initController")
@RestController
@RequiredArgsConstructor
public class InitController {
    @Value("${default.password}")
    private String defaultPassword;
    @Value("${admin.email}")
    private String adminEmail;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> initAdmin() {
        Optional<UserEntity> byEmail = userRepository.findByUsername(adminEmail);
        if (byEmail.isPresent()) {
            throw new BadRequestException("ADMIN already registered");
        }

        UserEntity entity = new UserEntity();
        entity.setName("Admin");
        entity.setEmail(adminEmail);
        entity.setUsername("admin");
        entity.setPassword(passwordEncoder.encode(defaultPassword));
        entity.setRole(Role.ADMIN);
        entity.setDeleted(Boolean.FALSE);
        entity.setIsLocked(Boolean.FALSE);
        entity.setFailAttempt(0);

        userRepository.save(entity);
        return ResponseEntity.ok("Admin added");
    }

}
