package com.example.student_management.controller;

import com.example.student_management.dto.LoginRequest;
import com.example.student_management.dto.RegisterRequest;
import com.example.student_management.entity.User;
import com.example.student_management.repository.UserRepository;
import com.example.student_management.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Username already exists");
        }

        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElse(null);

        if (user == null ||
                !passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword())) {

            return ResponseEntity.status(401)
                    .body("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());

        return ResponseEntity.ok(
                Map.of("token", token)
        );
    }
}