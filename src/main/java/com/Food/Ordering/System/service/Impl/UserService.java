package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.dto.UserDTO;
import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.EmailAlreadyExistException;
import com.Food.Ordering.System.exception.PhoneAlreadyExistException;

import java.util.Optional;

public interface UserService {

   User registerUser(UserDTO userDTO) throws EmailAlreadyExistException, PhoneAlreadyExistException;

   Optional<String> loginUser(String email, String password);
}
