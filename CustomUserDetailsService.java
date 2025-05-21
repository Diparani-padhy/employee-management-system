package com.employeemanagement.service;

import com.employeemanagement.entity.Users;
import com.employeemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users=userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("user not found"+username));
        return new org.springframework.security.core.userdetails.User(
                users.getUsername(),
                users.getPassword(),
                Collections.emptyList()
        );
    }
}
