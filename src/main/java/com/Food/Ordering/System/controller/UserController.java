package com.Food.Ordering.System.controller;

import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.EmailNotFoundException;
import com.Food.Ordering.System.exception.PhoneNotFoundException;
import com.Food.Ordering.System.exception.UserNotFoundException;
import com.Food.Ordering.System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Get user by email
    @GetMapping("/byEmail")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        if(user == null){
            throw new EmailNotFoundException("Email not exist");
        }
        return ResponseEntity.ok(user);
    }

    // Get user by phone
    @GetMapping("/byPhone")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserByPhone(@RequestParam String phone) throws PhoneNotFoundException {

            User user = userService.getUserByPhone(phone);
            if (phone == null) {
                throw new PhoneNotFoundException("Phone not exist");
            }
            return ResponseEntity.ok(user);
    }

    // Get user by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all users
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    // Delete user by ID
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}



