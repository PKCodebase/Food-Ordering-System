package com.Food.Ordering.System.service.Impl;


import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service

public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //It is a interface it have one abstract method loadUserbyusername
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user = optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email)
        );

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),   // Use email as the username
                user.getPassword(),
                Collections.emptyList()
        );
    }
}