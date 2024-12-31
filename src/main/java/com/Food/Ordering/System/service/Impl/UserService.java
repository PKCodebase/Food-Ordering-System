package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.dto.UserDTO;
import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.*;

import java.util.Optional;

public interface UserService {

   User registerUser(UserDTO userDTO) throws EmailAlreadyExistException, PhoneAlreadyExistException, PasswordValidationException;

   Optional<String> loginUser(String email, String password) throws UserNotFoundException, IncorrectPasswordException;

   String forgotPassword(String email) throws UserNotFoundException;

   String changePassword(String email, String oldPassword, String newPassword) throws UserNotFoundException, IncorrectPasswordException;
}
