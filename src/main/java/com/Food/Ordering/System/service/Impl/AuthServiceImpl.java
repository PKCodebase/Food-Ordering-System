package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.*;
import com.Food.Ordering.System.repository.AuthRepository;
import com.Food.Ordering.System.service.AuthService;
import com.Food.Ordering.System.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil  jwtUtil;

    @Override
    public User registerUser(User userDTO) throws EmailAlreadyExistException, PhoneAlreadyExistException, PasswordValidationException {
        Optional<User> existingUserByEmail = authRepository.findByEmail(userDTO.getEmail());
        if (existingUserByEmail.isPresent()) {
            throw new EmailAlreadyExistException("Email already exists");
        }

        Optional<User> existingUserByPhone = authRepository.findByPhone(userDTO.getPhone());
        if (existingUserByPhone.isPresent()) {
            throw new PhoneAlreadyExistException("Phone number already exists");
        }

        // Password validation within the same method
        String password = userDTO.getPassword();

        // Password validation regex check
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        // If the password does not match the regex, throw PasswordValidationException
        if (!password.matches(passwordRegex)) {
            throw new PasswordValidationException("Password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character");
        }

        // Encode password and map DTO to Entity
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword().trim()));
        User user = modelMapper.map(userDTO, User.class);

        // Save the user to the repository
        return authRepository.save(user);
    }


    @Override
    public String loginUser(String email, String password) throws UserNotFoundException, IncorrectPasswordException {
        Optional<User> user = authRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        if (!passwordEncoder.matches(password,user.get().getPassword())) {
            throw new IncorrectPasswordException("Password is incorrect");
        }
        return jwtUtil.generateToken(user.get().getEmail(), Collections.singletonList(user.get().getRole().name()));
    }


    @Override
    public String changePassword(String email, String oldPassword, String newPassword) throws UserNotFoundException, IncorrectPasswordException {
        Optional<User> userOptional = authRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found with email: " + email);
        }

        User user = userOptional.get();

        // Verify old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IncorrectPasswordException("Old password is incorrect");
        }

        // Encode and set the new password
        user.setPassword(passwordEncoder.encode(newPassword));
        authRepository.save(user);

        return "Password changed successfully";
    }
}
