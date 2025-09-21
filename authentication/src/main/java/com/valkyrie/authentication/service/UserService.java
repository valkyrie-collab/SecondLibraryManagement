package com.valkyrie.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.valkyrie.authentication.config.TokenConfig;
import com.valkyrie.authentication.model.User;
import com.valkyrie.authentication.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepo;
    @Autowired
    private void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    private AuthenticationManager authenticationManager;
    @Autowired
    private void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {this.config = config;}

    public ResponseEntity<String> signUp(User user) {

        if (userRepo.findById(user.getUsername()).orElse(null) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user already present...");
        }

        user.setRole("ROLE_" + user.getRole().toUpperCase()).setPassword(
            new BCryptPasswordEncoder(12).encode(user.getPassword())
        );

        userRepo.save(user);

        return userRepo.findById(user.getUsername()).orElse(null) == null? 
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not saved...") : 
            ResponseEntity.status(HttpStatus.ACCEPTED).body("user saved successfully please login....");

    }

    public ResponseEntity<String> signIn(User user) {
        String token = null;
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            token = config.generateToken(user.getUsername(), authentication.getAuthorities());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(token);

    }

    public ResponseEntity<User> getUser(String username) {
        User user = userRepo.findById(username).orElse(null);

        return user != null? 
            ResponseEntity.status(HttpStatus.OK).body(user) : 
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
