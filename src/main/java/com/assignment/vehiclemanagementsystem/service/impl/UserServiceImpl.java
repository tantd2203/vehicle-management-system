package com.assignment.vehiclemanagementsystem.service.impl;

import com.assignment.vehiclemanagementsystem.repository.UserRepository;
import com.assignment.vehiclemanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
* @author: TanTD1
* @since: 06/08/2024 10:12
* @description:  UserServiceImpl  handles all business logic related to user
* @update:
*
* */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    
    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
