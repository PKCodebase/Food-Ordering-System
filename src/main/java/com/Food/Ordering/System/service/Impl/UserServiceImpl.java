package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.EmailNotFoundException;
import com.Food.Ordering.System.exception.PhoneNotFoundException;
import com.Food.Ordering.System.exception.UserNotFoundException;
import com.Food.Ordering.System.repository.UserRepository;
import com.Food.Ordering.System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) throws EmailNotFoundException {
       Optional<User> user = userRepository.findByEmail(email);
       if(user.isEmpty()){
           throw new EmailNotFoundException("Email not exist");
       }
       return user.get();
    }

    @Override
    public User getUserByPhone(String phone) throws PhoneNotFoundException {
        Optional<User> user = userRepository.findByPhone(phone);
        if(user.isEmpty()){
            throw new PhoneNotFoundException("Phone not exist");
        }
        return user.get();
       }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not exist");
        }
        return user.get();
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public String deleteUserById(Long id) throws UserNotFoundException {
      return userRepository.findById(id)
              .map(user -> {
                  userRepository.delete(user);
                  return "User deleted successfully";
              })
              .orElseThrow(() -> new UserNotFoundException("User not exist"));
    }
}
