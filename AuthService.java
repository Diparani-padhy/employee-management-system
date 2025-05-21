package com.employeemanagement.service;

import com.employeemanagement.dto.AuthRequest;
import com.employeemanagement.dto.RegisterRequest;
import com.employeemanagement.entity.Users;
import com.employeemanagement.repository.UserRepository;
import com.employeemanagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    //register
    public String register(RegisterRequest request){
        //check if username is exit or not
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("Username already exit!!");
        }
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return "User register successfully!!";
    }


    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    //login
    public String login(AuthRequest request) {
        //Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );
        // Load user details and generate JWT token //CustomUserDetailsService

        UserDetails userDetails=userDetailsService.loadUserByUsername(request.getUsername());
        return jwtUtil.generateToken(userDetails.getUsername());
    }
    //delete
    public void deleteUserBYId(long id) {
        userRepository.deleteById(id);
    }
}
