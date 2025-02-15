package com.Food.Ordering.System.controller;


import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.enums.Role;
import com.Food.Ordering.System.repository.UserRepository;
import com.Food.Ordering.System.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtUtil jwtUtil;



    // **User Registration**
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists with this email.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign default role if not provided
        if (user.getRole() == null) {
            user.setRole(Role.ROLE_USER); // 🔹 Using Enum instead of String
        }

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    // **User Login**
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Email and password cannot be empty"));
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            User loggedInUser = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

            String token = jwtUtil.generateToken(loggedInUser.getEmail(), loggedInUser.getRole().name());

            return ResponseEntity.ok(Map.of("token", token, "role", loggedInUser.getRole().name()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid email or password"));
        }
    }
}
