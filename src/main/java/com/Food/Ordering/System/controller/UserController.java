package com.Food.Ordering.System.controller;

import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.EmailNotFoundException;
import com.Food.Ordering.System.exception.PhoneNotFoundException;
import com.Food.Ordering.System.exception.UserNotFoundException;
import com.Food.Ordering.System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Get user by email
    @GetMapping("/byEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        if(user == null){
            throw new EmailNotFoundException("Email not exist");
        }
        return ResponseEntity.ok(user);
    }

    // Get user by phone
    @GetMapping("/byPhone")
    public ResponseEntity<User> getUserByPhone(@RequestParam String phone) throws PhoneNotFoundException {

            User user = userService.getUserByPhone(phone);
            if (phone == null) {
                throw new PhoneNotFoundException("Phone not exist");
            }
            return ResponseEntity.ok(user);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all users
    @GetMapping
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    // Delete user by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) throws UserNotFoundException {
        User userOptional = userService.getUserById(id);
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        return ResponseEntity.ok("User deleted successfully");
    }
}



