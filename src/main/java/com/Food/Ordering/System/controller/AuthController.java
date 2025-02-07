package com.Food.Ordering.System.controller;


import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.*;
import com.Food.Ordering.System.request.ChangePasswordRequest;
import com.Food.Ordering.System.service.AuthService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User userDTO) {
        try {
            User user = authService.registerUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registered Successfully");
        } catch (EmailAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        } catch (PhoneAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number already exists");
        }catch (PasswordValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One Capital letter,small letter,special symbol,number");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        try {
            String token = authService.loginUser(email, password);
            return ResponseEntity.ok(token);
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            String message = authService.changePassword(
                    request.getEmail(),
                    request.getOldPassword(),
                    request.getNewPassword()
            );
            return ResponseEntity.ok(message);
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
