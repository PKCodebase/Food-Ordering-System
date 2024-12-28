package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.dto.UserDTO;
import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.EmailAlreadyExistException;
import com.Food.Ordering.System.exception.NameCannotNullException;
import com.Food.Ordering.System.exception.PhoneAlreadyExistException;
import com.Food.Ordering.System.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User registerUser(UserDTO userDTO) throws EmailAlreadyExistException, PhoneAlreadyExistException {
        User user = modelMapper.map(userDTO, User.class);
        Optional<User> existingUserByEmail = userRepository.findByEmail(userDTO.getEmail());
        if (existingUserByEmail.isPresent()) {
            throw new EmailAlreadyExistException("Email already exists");
        }
        Optional<User> existingUserByPhone = userRepository.findByPhone(userDTO.getPhone());
        if (existingUserByPhone.isPresent()) {
            throw new PhoneAlreadyExistException("Phone number already exists");
        }
        if(user.getFullName() == null ){
            throw new NameCannotNullException("Name cannot be null");
        }
        return userRepository.save(user);
    }
    @Override
    public Optional<String> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return Optional.of("Login successful");
        } else {
            return Optional.empty();
        }
    }

}
