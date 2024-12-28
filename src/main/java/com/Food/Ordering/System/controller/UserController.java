package com.Food.Ordering.System.controller;

import com.Food.Ordering.System.dto.UserDTO;
import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.EmailAlreadyExistException;
import com.Food.Ordering.System.exception.PhoneAlreadyExistException;
import com.Food.Ordering.System.service.Impl.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (EmailAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        } catch (PhoneAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number already exists");
        }
    }
}
