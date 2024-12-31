package com.Food.Ordering.System.controller;

import com.Food.Ordering.System.dto.UserDTO;
import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.*;
import com.Food.Ordering.System.service.Impl.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            User user = userService.registerUser(userDTO);
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
            Optional<String> message = userService.loginUser(email, password);
            return ResponseEntity.ok(message);
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            String message = userService.forgotPassword(email);
            return ResponseEntity.ok(message);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UserDTO userDTO) {
        try {
            String message = userService.changePassword(
                    userDTO.getEmail(),
                    userDTO.getPassword(),
                    userDTO.getNewPassword()
            );
            return ResponseEntity.ok(message);
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
