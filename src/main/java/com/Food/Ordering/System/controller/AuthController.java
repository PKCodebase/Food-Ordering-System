    package com.Food.Ordering.System.controller;

    import com.Food.Ordering.System.entity.User;
    import com.Food.Ordering.System.repository.UserRepository;
    import com.Food.Ordering.System.util.JwtUtil;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.web.bind.annotation.*;

    import java.util.Map;
    import java.util.Optional;

    @RestController
    @RequestMapping("/auth")
    public class AuthController {


        private final PasswordEncoder passwordEncoder;
        private final UserRepository userRepository;
        private final AuthenticationManager authenticationManager;
        private final JwtUtil jwtUtil;

        public AuthController(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
            this.passwordEncoder = passwordEncoder;
            this.userRepository = userRepository;
            this.authenticationManager = authenticationManager;
            this.jwtUtil = jwtUtil;
        }

        // **User Registration**
        @PostMapping("/register")
        public ResponseEntity<String> registerUser(@RequestBody User user) {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists with this email.");
            }

            // **Ensure password is strong**
            if (user.getPassword() == null || user.getPassword().length() < 8) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be at least 8 characters long.");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Assign default role if not provided
            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("ROLE_USER");
            }

            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully.");
        }

        // **User Login with Query Parameters**
        @GetMapping("/login")
        public ResponseEntity<Map<String, String>> login(
                @RequestParam String email,
                @RequestParam String password) {

            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password)
                );

                Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
                if (optionalUser.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid email or password"));
                }

                User loggedInUser = optionalUser.get();
                String token = jwtUtil.generateToken(loggedInUser.getEmail(), loggedInUser.getRole());

                return ResponseEntity.ok(Map.of("token", token, "role", loggedInUser.getRole()));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid email or password"));
            }
        }

    }
