package com.Food.Ordering.System.service;

import com.Food.Ordering.System.dto.UserDTO;
import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.*;

public interface AuthService {

   User registerUser(UserDTO userDTO) throws EmailAlreadyExistException, PhoneAlreadyExistException, PasswordValidationException;

   String loginUser(String email, String password) throws UserNotFoundException, IncorrectPasswordException;

   String changePassword(String email, String oldPassword, String newPassword) throws UserNotFoundException, IncorrectPasswordException;
}
