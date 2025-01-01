package com.Food.Ordering.System.service;

import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.EmailNotFoundException;
import com.Food.Ordering.System.exception.PhoneNotFoundException;
import com.Food.Ordering.System.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    User getUserByEmail(String email) throws EmailNotFoundException;

    User getUserByPhone(String phone) throws PhoneNotFoundException;

    User getUserById(Long id) throws UserNotFoundException;

    List<User> getAllUser();

    String deleteUserById(Long id) throws UserNotFoundException;
}
